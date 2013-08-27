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
        publicationInstance.discardMembers()
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
  
}
