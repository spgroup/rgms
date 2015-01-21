package pages

import org.aspectj.weaver.patterns.WithinCodeAnnotationPointcut;
import org.hibernate.hql.ast.HqlSqlWalker.WithClauseVisitor;

import geb.Page;


class DissertationShowPage extends Page{
	static url = "dissertacao/show"

	static at = {
		GetPageTitle gp = new GetPageTitle()
		def currentDissertation = gp.msg("default.dissertacao.label")
		def currentTitle = gp.msg("default.show.label", [currentDissertation])

		title ==~ currentTitle
	}
	
	static content = {
		
	}
	
	def editDissertation(){
		$('a.edit').click()
	}
	
	def deleteDissertation(){
		assert withConfirm(true) {
			$('input.delete').click()
		}
	}

	def select(String e, v) {
		if (v == 'delete') {
			assert withConfirm(true) { $("form").find(e, class: v).click() }
		} else {
			$("form").find(e, class: v).click()
		}
	}
}
