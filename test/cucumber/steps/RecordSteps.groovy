import rgms.member.Record
import rgms.member.Member
import steps.RecordTestDataAndOperations
import pages.LoginPage
import pages.PublicationsPage
import pages.record.*
import static cucumber.api.groovy.EN.*

Given(~'^the system has only one record with status "([^"]*)"$') { String status ->
	RecordTestDataAndOperations.insertsRecord(status)
	def records = Record.findAllByStatus_H(status)
	assert records.size() == 1 && records.first() != null
}

Given(~'^the record with status "([^"]*)" is not associated to a member$') { String status ->
  assert RecordTestDataAndOperations.recordIsAssociated(status, false)
}

Given(~'^the record with status "([^"]*)" is associated to a member$') { String status ->
  assert RecordTestDataAndOperations.recordIsAssociated(status)
}

When(~'^I remove the record with status "([^"]*)"$') { String status ->
	def id = Record.findByStatus_H(status).id
	RecordTestDataAndOperations.deleteRecord(id)
}

Then(~'^the record with status "([^"]*)" is properly removed by the system$') { String status ->
	def record = Record.findByStatus_H(status)
	assert record == null
}

Then(~'^the record with status "([^"]*)" is not removed by the system$') { String status ->
	def record = Record.findByStatus_H(status)
	assert record != null
}

Given(~'^the system has only one record with status "([^"]*)" and this record has a null end date$') { String status ->
	def records = Record.findAllByStatus_H(status)
	def record = records.first()
	assert records.size() == 1 && record != null && record.end == null
}

When(~'^I update the record with status "([^"]*)" with an end date "([^"]*)"$') { String status, String end_str ->
	RecordTestDataAndOperations.updateRecord(status, end_str)
}

Then(~'^the record with status "([^"]*)" has end date "([^"]*)"$') { String status, String end_str ->
	def record = Record.findByStatus_H(status)
	assert record.end.format("dd/mm/yyyy").equals(end_str)
}

When(~'^I create the record with status "([^"]*)"$') { String status ->
	RecordTestDataAndOperations.createRecord(status)
}

Then(~'^the record with status "([^"]*)" is properly stored and the system has two records with this status$') { String status ->
	def records = Record.findAllByStatus_H(status)
	assert records.size() == 2 && records.get(0).status_H == status && records.get(1).status_H == status
}

Given(~'^I am logged$') { ->
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
}

When(~'^I click the record with status "([^"]*)" at the record list$') { String status ->
    at RecordPage
    page.visualizeRecord(status)
}

Then(~'^I am still at the visualize page of the record with status "([^"]*)"$') { String status ->
  at RecordVisualizePage
  page.checkRecordDetails(status)
}

When(~'^I click the edit button of the record$') {
		->
		at RecordVisualizePage
        page.editRecord()
}

When(~'^I set the status to "([^"]*)" and I click the save button$') { String status ->
    at RecordEditPage
    page.changeRecordDetails(status)
}

Then(~'^I am at the edit page of the record with status "([^"]*)"$') { String status ->
    at RecordEditPage
    assert page.statusIsEmpty()
}

Given(~'^I am at record list$') {->
	at PublicationsPage
	to RecordPage
}

When(~'^I click the create record option$') {->
	at RecordPage
	page.selectNewRecord()
}

Then(~'^I can fill the record details$') {->
	at RecordCreatePage
	page.fillRecordDetails()
}

Given(~'^I am at the visualize page of the record with status "([^"]*)"$') {
	String status ->
	at PublicationsPage
	to RecordPage
	page.visualizeRecord(status)
	at RecordVisualizePage
}

When(~'^I click to remove the record$') {->
	at RecordVisualizePage
	page.removeRecord()
}
