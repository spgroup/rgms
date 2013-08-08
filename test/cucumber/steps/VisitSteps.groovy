import com.sun.org.apache.xalan.internal.xsltc.compiler.Import;

import rgms.visit.Visitor
import pages.LoginPage
import pages.VisitPage
import pages.VisitCreatePage
import pages.VisitShowPage
import rgms.tool.TwitterTool
import rgms.tool.FacebookTool
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*
import static cucumber.api.groovy.Hooks.*

Given(~'^que o visitante "([^"]*)" nao esteja cadastrado no  sistema$') { String visitante ->
	 def visitor = Visitor.findByName(visitante)
	 assert visitor == null
}

When(~'^eu agendar uma visita para o visitante "([^"]*)" And inserir apenas a data "([^"]*)"$') { String visitante, String data ->
	TestDataAndOperations.createVisitor(visitante)
	TestDataAndOperations.agendaVisita(visitante,data,data)
}

Then(~'^o cadastro do vistante "([^"]*)" e armazenado no sistema e tambem uma visita com de incio igual e fim igual  a "([^"]*)"$') { String visitante, String data ->
	def visita = TestDataAndOperations.buscaVisita(visitante,data,data)
	assert visita != null
}

When(~'^eu agendar uma visita para o visitante "([^"]*)" e inserir data inicio igual a "([^"]*)" e inserir a data fim igual a "([^"]*)"$') { String visitante, String dataInicio, String dataFim ->
	TestDataAndOperations.createVisitor(visitante)
	TestDataAndOperations.agendaVisita(visitante,dataInicio,dataFim)
}

Then(~'^o cadastro do vistante "([^"]*)" e armazenado no sistema e tambem uma visita com data de incio igual a "([^"]*)" e data fim igual a "([^"]*)"$') { String visitante, String dataInicio, String dataFim ->
	def visita = TestDataAndOperations.buscaVisita(visitante,dataInicio,dataFim)
	assert visita != null
}

Given(~'^que o visitante "([^"]*)" esteja cadastrado no  sistema$') { String visitante ->
	TestDataAndOperations.createVisitor(visitante)
	 def visitor = Visitor.findByName(visitante)
	 assert visitor != null
}

Then(~'^uma visita para o visitante "([^"]*)" com de incio igual e fim igual  a "([^"]*)" e armazenada no sistemas$') { String visitante, String data ->
	def visita = TestDataAndOperations.buscaVisita(visitante,data,data)
	assert visita != null
}

Then(~'^uma visita para o visitante "([^"]*)" com de incio igual a "([^"]*)" e data fim igual a "([^"]*)" e armazenada no sistemas$') { String visitante, String dataInicio, String dataFim ->
	def visita = TestDataAndOperations.buscaVisita(visitante,dataInicio,dataFim)
	assert visita != null
}

//#if( $Twitter )

Given(~'^I am logged as "([^"]*)" and at the Add Visit Page$') { String userName ->
	to LoginPage
	at LoginPage
	page.fillLoginData(userName, "adminadmin")
	to VisitPage
}

When(~'^I try to create an visit  and I click on Share it in Twitter with "([^"]*)" and "([^"]*)"$') { String twitterLogin, String twitterPw ->
	  at VisitPage
	  page.selectNewVisit()
	  at VisitCreatePage
	  page.fillVisitDetailsTwitter()
	
	  at VisitShowPage
	  page.clickOnTwitteIt(twitterLogin, twitterPw)
	  at VisitShowPage
	 }

When(~'^I try to create an visit$') { ->
	at VisitPage
	page.selectNewVisit()
	at VisitCreatePage
  page.fillVisitDetails()


}

Then(~'^A twitter is added to my twitter account regarding the new visit "([^"]*)"$') { String visita ->
  assert TwitterTool.consult(visita)
 }

Then(~'^The visit "([^"]*)" is created but no twitter should be post$') { -> String visita ->
   assert TwitterTool.consult(visita) && assert !TwitterTool.consult(null
	 }

//#end
