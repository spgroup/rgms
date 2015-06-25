package rgms.publication

import org.springframework.dao.DataIntegrityViolationException

class AuxiliarController {

  def boolean check(Object id, Publication publicationInstance, String label, String classe) {
    if (!publicationInstance) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: label, default: classe), id])
      redirect(action: "list")
      return true;
    } else {
      return false;
    }
  }
  
  def delete(Object id, Publication publicationInstance, String label, String classe) {
    boolean isReturned = check(id, publicationInstance, label, classe);
    if(!isReturned){
      try{
        publicationInstance.removeFromPublications()
        publicationInstance.delete(flush: true)
        flash.message = message(code: 'default.deleted.message', args: [message(code: label, default: classe), id])
        redirect(action: "list")
      }
      catch (DataIntegrityViolationException e) {
        flash.message = message(code: 'default.not.deleted.message', args: [message(code: label, default: classe), id])
        redirect(action: "show", id: id)
      }
    }
  }

  def deleteMultiples(Object id1, Publication publicationInstance1, String label, String classe, Object id2, Publication publicationInstance2 ) {
    boolean isReturned1 = check(id1, publicationInstance1, label, classe);
    boolean isReturned2 = check(id2, publicationInstance2, label, classe);
    if(!(isReturned1 && isReturned2)) {
      try {
        publicationInstance1.removeFromPublications()
        publicationInstance2.removeFromPublications()
        publicationInstance1.delete(flush: true)
        publicationInstance2.delete(flush: true)
        flash.message1 = message(code: 'default.deleted.message', args: [message(code: label, default: classe), id1])
        flash.message2 = message(code: 'default.deleted.message', args: [message(code: label, default: classe), id2])
        redirect(action: "list")
      }
      catch (DataIntegrityViolationException e) {
        flash.message1 = message(code: 'default.not.deleted.message', args: [message(code: label, default: classe), id1])
        flash.message2 = message(code: 'default.not.deleted.message', args: [message(code: label, default: classe), id2])
        redirect(action: "show", id: id)
      }
    }
  }
  
}
