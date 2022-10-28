
function init()					//инициализация
{
    $(".tablesorter").tablesorter();

    RA.HTML.OnEnter("C_NUM_CARD",		    "search();");
    RA.HTML.OnEnter("C_DATE_KORR",	    "search();");
    RA.HTML.OnEnter("C_ID",	    "search();");
    RA.HTML.OnEnter("C_DATE_CREATE",	    "search();");
    RA.HTML.OnEnter("C_OTV",       "search();");

    RA.HTML.OnEnter("C_STATUS",	        "search();");

    search();
}
function search()				//поиск
{
    var Data =
        {
            ID_CARD	    : $("#C_ID").val(),

        };
    RA.GUI.Wait();
    RA.Protocol.AsyncSingleRequest("CardPO", "getList", Data, function (data) {
        RA.GUI.EndOfWait();

        printTable(data);
    } );
}
function printTable(data)		//отрисовать таблицу
{
    var str = "";

    for (var key in data)
    {

        str += 		"<tr ondblclick='openCard(" + data[key].ID_CARD + ")'>";
        str	+= 			"<td class='fil2'>" + data[key].CARD_NUMBER +" </td>";
        str	+= 			"<td class='fil3'>" + data[key].CARD_STATUS + "&nbsp;</td>";
        str	+= 			"<td class='fil4'>" + data[key].RESPONSIBLE + "&nbsp;</td>";
        str	+= 			"<td class='fil5'>" + data[key].ADJUSTMENT_DATE + "&nbsp;</td>";
        str	+= 			"<td class='fil6'>" + data[key].CREATION_DATE + "&nbsp;</td>";
        str	+= 			"<td class='fil7'>" + data[key].LETTER_NUMBER  + "&nbsp;</td>";
        str	+= 			"<td class='fil8'>" + data[key].SUBSYTE + "&nbsp;</td>";



        str += 		"</tr>";
    }

    $(".mdata").empty();
    $(".mdata").append(  $(str)  );
    $(".mdata").trigger("update");


}

function openCard(id)
{

    RA.Page.OpenGet("CardView.html", { id : id }, "CardView" + id);
}
// function XL() {                                                //        Кнопка записать в Excel
//     var myRows = [];
//     var $rows = $("#maintab tr").each(function (index) {
//
//         $cells = $(this).find("th, td");
//
//         myRows[index] = {};
//         $cells.each(function (cellIndex) {
//             myRows[index][cellIndex] = $.trim($(this).text());
//         });
//     });
//     var ws = XLSX.utils.sheet_add_json(ws, myRows, {
//         skipHeader: false,
//         origin: -1
//     });
//
//     var wb = XLSX.utils.book_new();
//     XLSX.utils.book_append_sheet(wb, ws);
//     var wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
//
//     saveAs(new Blob([wbout], { type: "application/octet-stream" }), "Catalog_SP.xlsx");
// }

function formDate(date)
{
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var dat = date.getDate();
    if (month<10) month = "0" + month;
    if (dat<10) dat = "0" + dat;
    return dat + "." + month + "." + year;
}
