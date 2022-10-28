//Клиентский ассистент фрэймворка Rainbow2
//система отображения текста ошибки
// $(document).ready( function () {
//     var str = "";
//     str += "<div id='ErrMsg' style='z-index: 10000' title='Клик, чтобы скрыть' onclick='R2.CloseError()'>";
//     str += "	<div class='mbl'>";
//     str += "		<table style='width: 100%'><tr>";
//     str += "		<td class='c1'> <div style='height: 40px;'></div> </td>";
//     str += "		<td class='c2'><b><font color='white' id='Emsg'>Сообщение об ошибке!</font></b></td>";
//     str += "		</tr></table>";
//     str += "	</div>";
//     str += "</div>";
//
//     $("body").prepend( str );
// } );


var RA =	//Rainbow assistant новые фронтенд функции
    {
        GUI : { 				//Работа с графическим интерфейсом
            PopUp :
                {
                    Show : function (id, text, color, timeout)
                    {
                        var str = "";
                        str +=	"<div class='RAPopUp' id='RAPopUp_" + id + "' title='Клик, чтобы скрыть' onclick='RA.GUI.PopUp.Hide(this.id.substring(8, this.id.length))' style='background-color: " + color + "'>";
                        str +=	text;
                        str +=	"</div>";

                        $("#RAPopUp_" + id).remove();
                        $("body").append(str);
                        $("#RAPopUp_" + id).slideDown(300);

                        window.setTimeout("RA.GUI.PopUp.Hide('" + id + "')", timeout);
                    },
                    Hide : function (id)
                    {
                        $("#RAPopUp_" + id).slideUp(300, function () {$("#RAPopUp_" + id).remove();} );
                    }
                },
            Wait: function (time) //Инициировать бар ожидания
            {
                this.PopUp.Show("wait", "<p align='center' style='margin: 0;' >Пожалуйста подождите!</p>", "#C0C0C0", 120000);
            },
            EndOfWait: function () //Выключить ожидалку
            {
                this.PopUp.Hide("wait");
            },
            Error: function (message)
            {
                this.PopUp.Show("error", "<table style='margin: 0;'><tr> <td> <div class='ErrIcon'></div> </td><td>" + RA.DataPrepare.ToHTML(message) + "</td></tr></table>", "red", 10000);
            },
            ShowMessages: function (messages)
            {
                for (var key in messages)
                {
                    this.PopUp.Show("Msg" + key, messages[key], "grey", 30000 );
                }
            },
            BeepField : function (id)		//Мигнуть полем
            {
                $(id).fadeTo(100, 0.1, function () { $(id).fadeTo(2000, 1); });
            },
            SignHelp : function (id)	//сделать хелп из дива
            {
                var str = "";
                str +=	"<div class='RAHelp' title='Показать справку' onclick='$(\"" + id + "\").dialog(\"open\")'>";
                str +=	"</div>";

                $("body").append(str);

                $(id).dialog({
                    autoOpen: false,
                    position: ["center","center"],
                    buttons: {
                        "Закрыть" : function () { $(id).dialog("close"); }
                    },
                    hide: "fade",
                    modal: "true",
                    height: "auto",
                    width: 800
                });
            },
            Inputlength : function (id, max){
                /*$("#" + id).after( $("<br>") ).after( $("<span>LOL</span>").change(function(){
                    alert("hello");
                    $(this).html( $("#" + id).val().length );
                }) ) ;*/
                $("#" + id).after( $("<br>") ).after( $("<span id='" + id + "_length'></span>") ).keyup(function(){
                    $("#" + id + "_length").html( $("#" + id).val().length + "/" + max );
                })  ;
            }
        },
        Page : {				//Работа со страницами
            CallPost: function (adress, data) {
                var frm = document.createElement("form");
                frm.method="post";
                frm.action = adress;
                document.body.appendChild(frm);
                for (var key in data)
                {
                    var param = document.createElement("input");
                    param.name = key;
                    param.id = key;
                    param.value = data[key];
                    param.type = "hidden";
                    frm.appendChild(param);
                }
                frm.submit();
            },
            OpenGet: function (adress, data, name) {
                var call = adress + "?";
                for (var key in data)
                {
                    call += (key + "=" + data[key] + "&");
                }
                window.open(call, name);
            },
            SetPage: function (adress, data) {
                var call = adress + "?";
                for (var key in data)
                {
                    call += (key + "=" + data[key] + "&");
                }
                window.location = call;
            }
        },
        Protocol : {//Работа с протоколами
            AsyncSingleRequest: function (protocol, action, Data, callback)			//Асинхронный вызов протокола с его выключением
            {
                var SendData =
                    {
                        protocol: protocol,
                        action: action,
                        data: JSON.stringify(Data)
                    };
                var japtions =
                    {
                        url: "/ARMZ/Rainbow2/ajax/ProtocolSingleCaller.jsp",
                        type: "POST",
                        timeout: 120000,
                        dataType: "json",
                        data: SendData,
                        async: true,
                        success: function (data)
                        {
                            if (data.status === "FAIL SQL")		//Ошибка SQL во время выполнения  протокола
                            {
                                RA.GUI.Error(data.message);
                                return;
                            }
                            if (data.status === "FAIL")			//Ошибка в протоколе
                            {
                                RA.GUI.Error(data.message);
                                return;
                            }
                            if (data.status === "UNAUTH")			//пропала авторизация
                            {
                                window.location = "/ARMZ/auth/relogonFailed.jsp";
                                return;
                            }
                            if (data.status === "SUCCESS")			//Успешный ответ
                            {
                                callback(data.data);
                                return;
                            }
                        }
                    };
                $.ajax(japtions);
            },
            AsyncRequest: function (protocol, action, Data, callback)				//Асинхронный вызов протокола
            {
                var SendData =
                    {
                        protocol: protocol,
                        action: action,
                        data: JSON.stringify(Data)
                    };
                var japtions =
                    {
                        url: "/ARMZ/Rainbow2/ajax/ProtocolCaller.jsp",
                        type: "POST",
                        timeout: 120000,
                        dataType: "json",
                        data: SendData,
                        async: true,
                        success: function (data)
                        {
                            if (data.status === "FAIL SQL")         //Ошибка SQL во время выполнения протокола
                            {
                                RA.GUI.Error(data.message);
                                return;
                            }
                            if (data.status === "FAIL")             //Ошибка в протоколе
                            {
                                RA.GUI.Error(data.message);
                                return;
                            }
                            if (data.status === "UNAUTH")			//пропала авторизация
                            {
                                window.location = "/ARMZ/auth/relogonFailed.jsp";
                                return;
                            }
                            if (data.status === "SUCCESS")			//Успешный ответ
                            {
                                callback(data.data);
                                return;
                            }
                        }
                    };
                $.ajax(japtions);
            },
        },
        DataPrepare : {			//Подготовка данных
            ToValue : function (input)		//вывод данных в value
            {	//функция формирования строки для вывода на HTML страницу
                //принимает исходную строку, возвращает с заменой символов < > "
                var out;
                out = input.replace(/"/g, "&#34;");
                out = out.replace(/</g, "&#60;");
                out = out.replace(/>/g, "&#62;");
                return out;
            },
            ToHTML : function (input)		//вывод даных в код
            {	//функция формирования строки для вывода на HTML страницу
                //принимает исходную строку, возвращает с заменой символов < > " и перевод строки
                var out;
                out = input.replace(/"/g, "&#34;");
                out = out.replace(/</g, "&#60;");
                out = out.replace(/>/g, "&#62;");
                out = out.replace(/\n/g, "<br>");
                return out;
            }
        },
        OBJ : { 				//JavaScript Object Assistant
            Length : function (object)		//Возвращает количество элементов объекта (детей)
            {
                var i = 0;
                for (var key in object) i++;
                return i;
            },
            Clone : function (o)
            {
                return eval("("+JSON.stringify(o)+")");
            }
        },

        HTML : {				//Обработка в HTML
            SortSelect : function (select_id)
            {
                var sortedVals = $.makeArray($("#"+select_id+" option:not(.secondary)")).sort(function(a,b){
                    return $(a).text() > $(b).text() ? 1 : $(a).text() < $(b).text() ? -1 : 0 ;
                });
                var secondaryVals = $.makeArray($("#"+select_id+" option.secondary")).sort(function(a,b){
                    return $(a).text() > $(b).text() ? 1 : $(a).text() < $(b).text() ? -1 : 0 ;
                });
                $("#"+select_id).empty().append(sortedVals).append(secondaryVals);
            },
            fullPercWrap : function fullPercWrap (selector)
            {
                $(selector).wrap("<div class='inwrap'>", "</div>");
            },
            OnEnter: function (id, exec)
            {
                $("#" + id).wrap("<form onsubmit='" + exec + "; return false;' style='display: inline'></form>");
            }
        },
        AJAX : {				//Помошники AJAX
            Fast: function (adress, sendData)		//Быстрая отправка AJAX запроса на сервер. Синхронная до 2 секунд
            {
                var japtions = {
                    url: adress,
                    type: "POST",
                    timeout: 2000,
                    dataType: "json",
                    data: sendData,
                    async: false
                };
                var data = $.ajax(japtions);
                try
                {
                    var retobj = eval('(' + data.responseText + ')');
                }
                catch (Exception)
                {
                    this.GUI.Error("Не удалось распознать данные AJAX");
                    return {};
                }
                if (retobj === "AJAX failure")
                {
                    this.GUI.Error("Ошибка выполнения AJAX запроса");
                    return {};
                }
                return retobj;
            }
        },
        Data : {
            leftZeroTrim: function (str) {			//удалить нули слева
                var tmp = str;
                while (tmp.length>0 && tmp.charAt(0)=='0') {
                    tmp = tmp.substring(1);
                }
                return tmp;
            }
        }
    };