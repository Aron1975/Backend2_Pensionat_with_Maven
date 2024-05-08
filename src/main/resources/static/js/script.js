$(function () {
    $("#startdatepicker").datepicker({
        autoclose: true,
        todayHighlight: true,
        startDate: new Date(),
    }).datepicker('update', new Date())
  //  let stDate = new Date();
  //  stDate.setDate(stDate.getDate() + 1);
    $("#stopdatepicker").datepicker({
        autoclose: true,
        todayHighlight: true,
        startDate: new Date(),
    }).datepicker('update', new Date());
});