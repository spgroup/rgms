package rgms.publication

import org.springframework.dao.DataIntegrityViolationException

class FerramentaControllerAuxiliar {

    def boolean checkVersionFailed(Long version, Ferramenta ferramentaInstance) {
        if (ferramentaInstance.version > version) {
            ferramentaInstance.errors.rejectValue(
                "version", "default.optimistic.locking.failure",
                [message(code: 'ferramenta.label', default: 'Ferramenta')] as Object[],
                message(code: 'ferramenta.edit.conflict'))
                return true
        }
        else{
            return false
        }
    }
}
