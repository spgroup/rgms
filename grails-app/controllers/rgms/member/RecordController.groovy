package rgms.member

import org.springframework.dao.DataIntegrityViolationException

import rgms.member.Record
import rgms.member.Member

class RecordController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[recordInstanceList: Record.list(params), recordInstanceTotal: Record.count()]
	}

	def create() {
		[recordInstance: new Record(params)]
	}

	def checkVersionInconsistence(def checked_version, def recordInstance)
	{
		if (checked_version) {
			def version = checked_version.toLong()
			if (recordInstance.version > version) {
				recordInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'Record.label', default: 'Record')] as Object[],
						  "Another user has updated this Record while you were editing")
				render(view: "edit", model: [recordInstance: recordInstance])
				return true
			}
		}
		return false
	}

	def update() {
		def recordInstance = tryFindInstance()?.recordInstance
		if (recordInstance) {
		   def inconsistence = checkVersionInconsistence(params.version,recordInstance)
		   if(!inconsistence){
			   recordInstance.properties = params
			   trySave(recordInstance,"edit","updated")
		   }
		}
	}

	def save() {
		def recordInstance = new Record(params)
		trySave(recordInstance,"create","created")
	}

	def trySave(def recordInstance,def viewToRender, def messageCode)
	{
		if (!recordInstance.save(flush: true)) {
			render(view: viewToRender, model: [recordInstance: recordInstance])
			return
		}

		flash.message = message(code: 'default.'+messageCode+'.message', args: [message(code: 'Record.label', default: 'Record'), recordInstance.id])
		redirect(action: "show", id: recordInstance.id)
	}

	def tryFindInstance()
	{
		def recordInstance = Record.get(params.id)
		if (!recordInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
			redirect(action: "list")
			return
		}

		[recordInstance: recordInstance]
	}

	def show() {
		tryFindInstance()
	}

	def edit() {
		tryFindInstance()
	}

	def tryDelete(def recordInstance)
	{
		try {
			checkAssociations(recordInstance.id)
			recordInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'Record.label', default: 'Record'), params.id])
			redirect(action: "show", id: params.id)
		}
	}

	def delete() {
		def recordInstance = tryFindInstance()?.recordInstance
		if (recordInstance) {
			tryDelete(recordInstance)
		}
	}


	static public def checkAssociations(def recordId) {
		if(recordHasMembers(recordId)) {
			throw new DataIntegrityViolationException("Este histórico não pode ser apagado, pois ele está associado a um membro")
		}
	}

	static public def recordHasMembers(def recordId) {
		def c = Member.createCriteria()
		def members = c.listDistinct{
			historics{
				eq("id", recordId)
			}
		}
		members.size() > 0
	}
}