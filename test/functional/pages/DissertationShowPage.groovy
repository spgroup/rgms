package pages

import org.aspectj.weaver.patterns.WithinCodeAnnotationPointcut;
import org.hibernate.hql.ast.HqlSqlWalker.WithClauseVisitor;

import geb.Page;


class DissertationShowPage extends Page{
	static url = "dissertacao/show"
	
	static at = {
		title ==~ /Ver Dissertação/
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
}
