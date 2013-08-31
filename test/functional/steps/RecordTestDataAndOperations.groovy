package steps

import rgms.member.Record
import rgms.member.RecordController

class RecordTestDataAndOperations {
  static records = [
    [status_H: "MSc Student", start: (new Date()), end: null],
    [status_H: "Graduate Student", start: (new Date()), end: null]
  ]

  static public def findRecordByStatus(def status) {
      records.find { record ->
      record.status_H == status
    }
  }

  static public boolean recordIsAssociated(def status, def shallBe = true) {
    def recordId = Record.findByStatus_H(status).id
    RecordController.recordHasMembers(recordId) == shallBe
  }

  static public def createRecord(def status) {
    def cont = new RecordController()
    def record = findRecordByStatus(status)
    cont.params.status_H = record.status_H
    cont.params.start = record.start
    cont.params.end = record.end
    cont.create()
    cont.save()
    cont.response.reset()
  }

  static public def insertsRecord(String status) {
    def inserted = Record.findByStatus_H(status)
    if (!inserted) {
      def record = findRecordByStatus(status)
      Record r = new Record()
      r.setStatus_H(record.status_H)
      r.setStart(r.start)
      r.setEnd(r.end)
      r.save()
    }
  }

  static public void deleteRecord(def id) {
    def rec = new RecordController()
    rec.params.id = id
    rec.request.setContent(new byte[1000]) // Could also vary the request content.
    rec.delete()
    rec.response.reset()
  }

  static public void updateRecord(def status, String end) {
    def record = Record.findByStatus_H(status)
    def rec = new RecordController()
    rec.params.id = record.id
    rec.params.start = record.start
    rec.params.status_H = record.status_H
    rec.params.end = Date.parse("dd/mm/yyyy", end)
    rec.request.setContent(new byte[1000]) // Could also vary the request content.
    rec.update()
    rec.response.reset()
  }
}
