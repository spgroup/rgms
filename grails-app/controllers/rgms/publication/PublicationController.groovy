package rgms.publication

import org.apache.shiro.SecurityUtils
import rgms.member.Member
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;
import rgms.authentication.User

class PublicationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        render(view: "publication")
    }
//#if($Bibtex)
    def generateBib() {
        def publication = Publication.get(params.id)
        render(text: publication.generateBib(), contentType: "text/txt", encoding: "UTF-8")
    }
//#end

//#if($contextualInformation)
    def static Member addAuthor(Publication publication) {
        Member user = getLoggedMember()
        if (user) {
            publication.addMember(user)
        }
        return user
    }

    def static Set membersOrderByUsually() {
        Member user = getLoggedMember()
        def members = new LinkedHashSet<Member>()
        if (user) {
            members.addAll(Member.listOrderByName().sort({ it -> return -(it == user ? (user.publications.size() + 1) : (user.publications.intersect(it.publications)).size()) }))
        } else {
            members.addAll(Member.listOrderByName())
        }
        return members
    }

    def static Member getLoggedMember() {
        Member author = null
        try {
            if (SecurityUtils.subject?.principal != null) {
                User user = User.findByUsername(SecurityUtils.subject.principal)
                author = user.getAuthor()
            }
        } catch (org.apache.shiro.UnavailableSecurityManagerException e) {
            return null
        } finally {
            return author
        }
    }
//#end

    def upload(Publication publicationInstance) {

        def originalName = publicationInstance.file
        def filePath = "web-app/uploads/${originalName}"
        publicationInstance.file = filePath

        def f = new File(filePath)

        if (f.exists()) {
            flash.message = message(code: 'file.already.exist.message', default: 'File already exists. Please try to use a different file name.')
            return false
        }

        InputStream inputStream = request.getInputStream()
        OutputStream outputStream = new FileOutputStream(f)
        byte[] buffer = new byte[1024 * 10] //buffer de 10MB
        int length

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.close()
        inputStream.close()

        return true
    }

    def static newUpload(Publication publicationInstance, flash, request) {

        def originalName = publicationInstance.file
        def filePath = "web-app/uploads/${originalName}"
        publicationInstance.file = filePath

        def f = new File(filePath)
        if (f.exists()) {
            flash.message = 'File already exists. Please try to use a different file name.'
            return false
        }
        InputStream inputStream = request.getInputStream()
        OutputStream outputStream = new FileOutputStream(f)
        byte[] buffer = new byte[1024 * 10] //buffer de 10MB
        int length

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.close()
        inputStream.close()

        return true
    }

	/**
	*   Para enviar o post, é preciso esta autenticado com o facebook.
	*      - Para um usuario novo, no momento do registro basta logar com o FB
	*      - Para um usuario ja existente, edit esse usuario e adicione o a conta do FB ao usuario.
	*/
	//#if($facebook)
	def static sendPostFacebook(Member user, String title){
        def url = "https://graph.facebook.com/me/feed?access_token=" + user?.access_token 
        System.out.println(title);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        HttpPost post = new HttpPost(url);

        params.add(new BasicNameValuePair("access_token", user?.access_token));
        params.add(new BasicNameValuePair("message", "Confira a minha nova publicação no sistema RGMS! O title da publicação é " + title));

        System.out.println("Confira a minha nova publicação no sistema RGMS! O title da publicação é " + title);

        UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        post.setEntity(postEntity);
        
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        StatusLine statusLine = response.getStatusLine();
        
//      return statusLine.getStatusCode();
    }
    //#end
}
