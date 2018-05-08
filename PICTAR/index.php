<?php
    require_once __DIR__.'/config.php';
    require_once __DIR__.'/logica.php';

    if (!isset($_SESSION['numElementos'])){
        $_SESSION['numElementos'] = null;
    }
    if(!isset($_SESSION['numColumnas'])){
        $_SESSION['numColumnas'] = null;
    }
    if(!isset($_SESSION['elementosGuardados'])){
        $_SESSION['elementosGuardados'] = null;
    }
    if(!isset($_SESSION['endRefresh'])){
        $_SESSION['endRefresh'] = false;
    }
 ?>
<html>

<head>
    
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-114462800-1"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());

      gtag('config', 'UA-114462800-1');
    </script>

    
	<title>PICTAR</title>

	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="style.css">

    <script src="jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <script src="jquery.ui.touch-punch.min.js"></script>
    <script src="moment.js"></script>

    <link rel="stylesheet" type="text/css" href="tooltipster/dist/css/tooltipster.bundle.min.css" />
    <script type="text/javascript" src="tooltipster/dist/js/tooltipster.bundle.min.js"></script>

   <script src="jquery-printme.js"></script>

</head>

<body>
    <div class="header-container">
        <div class="w3-container">
            <div class="col hide-button-container">
                <button id="hide" class="hide-button" data-toggle="collapse" data-target="#translator-container">
                    <!--<span class="glyphicon glyphicon-eye-open"></span>-->
                    <!--<img class="image-hide-button" src="ocultar.png"></img>-->
                </button>
            </div>
            <div class="col title-container">
                <h1 class="title"> PICTAR </h1>
            </div>
        </div>

        <div id="translator-container" class="w3-container w3-border collapse in">
            <div class="translator">
                <h2> Traducir frase </h2>
                <div class="input-group stylish-input-group search-bar">
                    <input type="text" id="textToTranslate" class="form-control"  placeholder="Frase a traducir" >
                    <span class="input-group-addon">
                        <button type="submit" id="translate">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>  
                    </span>
                </div> 
                <div class="result-translator">
                    <div id = "grid-translator"></div>
                    <!--<button type="submit" class="arrow-icon glyphicon glyphicon-arrow-down"/>-->
                    <button id="moveTranslation" type="submit" data-toggle="tooltip" title="Mover hacia abajo" class="arrow-icon hide-arrow-icon">
                        <image class="image-arrow-icon" src="moverAbajo.png">
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="w3-row w3-border main-container">
        <div class="w3-twothird w3-container">
            <h2>Editor</h2>
            <div class="header-editor"> 
                <div class ="rc-editor-container">
                    <h3> Elementos </h3>
                    <input id="numElements" maxlength="2" data-toggle="tooltip" title="Elementos" class ="rcb-editor input-lg" type="text" name="elements" onkeypress="return justNumbers(event)">  
                    <h3> Columnas </h3>
                    <input id="numColumns" maxlength="2" data-toggle="tooltip" title="Columnas"class ="rcb-editor input-lg" type="text" name="columns" onkeypress="return justNumbers(event)">
                    <button type="button" id="generateGridButton" class="generate-table-button"> Generar </button>
               
                    <div id="text-field" class = "text-field-button">
                        <button class="btn btn-primary-text dropdown-toggle" type="button" data-toggle="dropdown"></button>
                        <ul class="dropdown-menu">
                          <li id ="top"><a href="#">Arriba</a></li>
                          <li id ="down"><a href="#">Abajo</a></li>
                          <li id ="none"><a href="#">Ninguno</a></li>
                        </ul>
                    </div>

                    <div id="color" class = "text-field-button">
                        <button class="btn btn-primary-color dropdown-toggle" type="button" data-toggle="dropdown"></button>
                        <ul class="dropdown-menu">
                          <li class="dropdown-submenu">
                              <a class="tool" tabindex="-1"> Color <span class="caret"></span></a>
                              <ul class="dropdown-menu">
                                 <li id="black"><a tabindex="-1" href="#">Blanco y Negro</a></li>
                                 <li id="original"><a tabindex="-1" href="#">Original</a></li>
                              </ul>
                          </li>
                        </ul>
                        <!--<button class="btn btn-primary-color dropdown-toggle" type="button" data-toggle="dropdown"></button>
                        <ul class="dropdown-menu multi-level">
                            <li id="salvar"><a href="#">Salvar</a></li>
                            <li class="divider"></li>
                            <li class="dropdown-submenu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Color</a>
                                <ul class="dropdown-menu">
                                    <li id="black"><a href="#">Blanco y Negro</a></li>
                                    <li id="original"><a href="#">Original</a></li>
                                </ul>
                            </li>
                        </ul>-->
                    </div>

                    <div id="import-export" class = "text-field-button">
                        <button class="btn btn-primary-menu dropdown-toggle" type="button" data-toggle="dropdown"></button>
                        <input name="imp" type="file" id="importar"/>
                        <ul class="dropdown-menu">
                          <li id ="import"><a href="#" id="importRef">Importar</a></li>
                          <li id ="download"><a href="#" id="downloadLink">Exportar</a></li>
                        </ul>
                    </div>

                    <!--<div id="print" class= "text-field-button print-button">
                        <button id="printButton"class="btn btn-primary hidden-print"><span class="glyphicon glyphicon-print" aria-hidden="true"></span> Imprimir</button>
                    </div>-->
                    <!-- <div id="download" class= "text-field-button export-button">
                        <a href="" id="downloadLink"> 
                            <button class="btn btn-download" type="button"></button>
                        </a>
                    </div>
                    <div id="import" class= "text-field-button import-button">
                        <button class="btn btn-import" type="button"></button>
                        <input name="imp" type="file" id="importar">
                    </div>-->

                </div>
            </div>
            <div id="grid-editor"></div>
        </div>
        <div class="w3-third w3-container search-container">
            <div>
                <h2>Buscador</h2>
                <div class="input-group stylish-input-group search-bar-word">
                    <input type="text" id="wordToSearch" class="form-control"  placeholder="Buscar" onkeyup="buscar()">
                    <span class="input-group-addon">
                        <button type="submit" id="searchButton">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>  
                    </span>
                </div> 
                <div id="grid-seeker"></div>
            </div>
            <!--
            <div>
                <h2>Importar imagen</h2>
                <div class="">
                    <button></button>
                </div>
            
            </div>
            -->
        </div>
    </div>

    <div>
        <br>
        <br>
    </div>
</body>
</html>

<script>

var grid = document.querySelector('#grid-editor');
var gridSeeker = document.querySelector('#grid-seeker');
var gridTranslator = document.querySelector('#grid-translator');
var fileInput = document.getElementById('importar');
var editorWidth;
var seekerWidth;
var searchBox;
var box;
var textBox;

var numElementos;
var numColumnas;
var phpElements = "<?php echo $_SESSION['numElementos'];?>";
var phpColumnas = "<?php echo $_SESSION['numColumnas']; ?>";

var load = false;
var ok = true;

if (phpElements){
    numElementos = phpElements;
}else{
    numElementos = $("#numElements").val();
}
if(phpColumnas){
    numColumnas = phpColumnas;
}else{
    numColumnas = $("#numColumns").val();
}

$(document).ready(function(){

  $("#importRef").on('click', function(e){
        $("#importar").trigger("click");
  });  

  $("#importar").on('change', function(e){
    var file = fileInput.files[0];
    if(file!=null){
        var textType = /text.*/;
        if (file.type.match(textType)) {
            readFile(file, function(e) {
                cf = check_file(e.target.result);
                if(cf[0]){
                    load_file(e.target.result,cf[1]);
                }else{
                    alert("Archivo incorrecto");
                }
                
            });
        } else {
            alert("Archivo no soportado");
        }
    }
    
  });

   //hide.bs.collapse -> Se produce cuando el elemento colapsable está a punto de ocultarse 
  $("#translator-container").on("hide.bs.collapse", function(){
    //$(".hide-button").html('<span class="glyphicon glyphicon-eye-close"></span>');
    //$(".hide-button").html('<img class="image-hide-button" src="mostrar.png"></img>');
    document.getElementById("hide").style.backgroundImage = "url('./mostrar.png')";
  });
  //show.bs.collapse -> Se produce cuando el elemento colapsable está a punto de mostrarse
  $("#translator-container").on("show.bs.collapse", function(){
    //$(".hide-button").html('<span class="glyphicon glyphicon-eye-open"></span>');
    //$(".hide-button").html('<img class="image-hide-button" src="ocultar.png"></img>');
    document.getElementById("hide").style.backgroundImage = "url('./ocultar.png')";
  });

  $(".dropdown-submenu a.tool").on("click", function(e){
    $(this).next('ul').toggle();
    e.stopPropagation();
    e.preventDefault();
  });
    
  //$('[data-toggle="tooltip"]').tooltip(); 
  $('.tooltip').tooltipster();

  //Entra cuando se refreca la página
  $(window).on('load', function(){
    if(getSaveElements()=='null'){
        document.getElementById("numElements").value = "10";
        document.getElementById("numColumns").value = "5";
        generarGrid(10,5,true,0);
    }else{
        generarGrid(numElementos,numColumnas,false,1);
        document.getElementById("numElements").value = numElementos;
        document.getElementById("numColumns").value = numColumnas;
    }
    
  });

  /*function restablecerValoresTexto(){
    idResult = 0;
    resultado = <?php echo json_encode($_SESSION['elementosGuardados']);?>;
    $('div.grid-container-editor>div>div').each(function(){ 
        if(idResult<resultado.length){
            if($(this).attr('class')=="box-text-container-top"){
                $(this).children().get(0).value = resultado[idResult]['valueTopText'];
            }
            if($(this).attr('class')=="box-text-container-bottom"){
                $(this).children().get(0).value = resultado[idResult]['valueBottomText'];
                idResult++;
            }
        }
        
     }); 
  }*/
  /*function restablecerPicto(){
    alert("hola");
    resultadoPicto = [{"id":"image-5","url_final":"8155.png"},{"id":"image-8","url_final":"2248.png"}];
    //resultadoPicto = "<?php echo json_encode($_SESSION['elementosGuardados']);?>";
    if(resultadoPicto.length !== 0){
        $(resultadoPicto).each(function(){
            var url = "http://hypatia.fdi.ucm.es/conversor/Pictos/" + $(this).attr('url_final');
            document.getElementById("#image-3").src = url;
            //console.log($(this).attr('id'));
        });
    }
  }*/

  $("#generateGridButton").click(function(){
    if(!$("#numElements").val()){
        alert("Se necesita un número de elementos");
        $("#numElements").val(getNumElementsGridEditor());
    }
    else if(!$("#numColumns").val()){
        alert("Se necesita un número de columnas");
        $("#numColumns").val(getNumColumnsGridEditor());
    }else{
        resultado = getSaveElements();
        $("#generateGridButton").attr("disabled","true"); 
        if(resultado == null){
            generarGrid($("#numElements").val(),$("#numColumns").val(),true,0);
        }else{
            generarGrid($("#numElements").val(),$("#numColumns").val(),true,1);
        }
        $("#generateGridButton").removeAttr("disabled"); 
    }
  });

  $(window).resize(function(){

        if($('#numColumns').val() == ''){
            numColumns = "<?php echo $_SESSION['numColumnas']; ?>";
        }else{
            numColumns = $('#numColumns').val();
        }
        
        editorWidth = grid.clientWidth;
        seekerWidth = gridSeeker.clientWidth;
        translatorWidth = gridTranslator.clientWidth;
        searchBox = seekerWidth/(2*1.3);
        box = editorWidth/(numColumns*1.3);
        newHeightSearchBox = 170-(180 - (searchBox*1.1));
        textBox = editorWidth/(numColumns*1.3);
        translatorBox = translatorWidth/(5*1.3);
        newHeightTranslatorBox = 170-(180 - (translatorBox*1.1));

       
        $(".box-grid-container").css({"width": box});
        $(".box-grid-container").css({"height": box});
        $(".clean-remove-buttons").css({"width": box});
        $(".clean-remove-buttons").css({"height":box});
        $(".seeker-image").css({"width": searchBox});
        $(".seeker-image").css({"height": newHeightSearchBox});
        $(".box-text-container-top").css({"width": textBox});
        $(".box-text-container-bottom").css({"width": textBox});
        $(".translator-image").css({"width": translatorBox});
        $(".translator-image").css({"height": newHeightTranslatorBox});
        
  });

  $("#wordToSearch").on('keyup', function (e) {
    if (e.keyCode == 13) {
        buscar();
    }
  });
  $("#textToTranslate").on('keyup', function (e) {
    if (e.keyCode == 13) {
        traducir();
    }
  });


  $("#translate").click(function(){
        traducir();
  });

  $("#searchButton").click(function(){
        buscar();
  });

  $("#moveTranslation").click(function(){
    var lastId="image-0";
    $('div.grid-container-editor>div>div>img').each(function(){ 
        var id = $(this).attr('id');
        var url = $(this).attr('src');
        if(url != "./blanco.png"){
            lastId = id;
        }
    });
    lastId = lastId.split("-")[1];
    totalElements = getNumElementsGridEditor();
    space = totalElements - lastId;
    translate = 0;
    resultadoPictos = [];
    $('div.grid-container-translator>div>div>img').each(function(){ 
        var url = $(this).attr('src');
        if(url == "./error.png"){
            url = "./blanco.png";
        }
        var item = {url};
        translate++;
        resultadoPictos.push(item);
    });
    if(resultadoPictos.length > 0){

        if(space >= translate){
            count =1;
            idResult =0;
            terminado = false;
            $('div.grid-container-editor>div>div>img').each(function(){ 
                var id = "#" + $(this).attr('id');
                if(count > lastId && !terminado){
                    if(idResult>=resultadoPictos.length){
                        terminado = true;
                    }else{
                        url = resultadoPictos[idResult]['url'];
                        $(id).attr("src",url);
                    }
                    idResult++;
                }
                count++;
            });
        }else{
            //cajasNuevas = translate-space;
            //columnas = "<?php echo $_SESSION['numColumnas']; ?>";
            //for(j=0;j<cajasNuevas;j++){
              //  addNewBox(columnas);
            //}
            alert("No hay suficiente espacio, añada nuevas cajas para mover la traducción");
            
        }
    }
    save();
    //alert(translate);
    //alert(lastId);
    //alert(space);
  });
 
  $("#top").click(function(){
    arrayTexto = []; 
    $('div.grid-container-editor>div>div').each(function(){ 
        if($(this).attr('class')=="box-text-container-bottom"){
            id = $(this).attr('id').split("-")[3];
            value = $(this).children().get(0).value;
            arrayTexto.push({id,value});
        }
    });
    count = 0;
    terminado = false;
    $('div.grid-container-editor>div>div').each(function(){  
      if(!terminado && $(this).attr('id')=="box-text-top-"+arrayTexto[count]['id']){
        if($(this).attr('class')=="box-text-container-top"){
            $(this).children().get(0).value = arrayTexto[count]['value'];
          count++;
          if(count >= arrayTexto.length){
            terminado = true;
          }
        }
       }
    });
    $(".box-text-container-top").css({"display":'block'});
    $(".box-text-container-bottom").css({"display":'none'});

  });
  $("#down").click(function(){
     $('div.grid-container-editor>div>div').each(function(){ 
        if($(this).attr('class')=="box-text-container-top"){
            textValue = $(this).children().get(0).value;
        }
        if($(this).attr('class')=="box-text-container-bottom"){
            $(this).children().get(0).value = textValue;
        }
     }); 
    $(".box-text-container-top").css({"display":'none'});
    $(".box-text-container-bottom").css({"display":'block'});
  });
  $("#none").click(function(){
    $(".box-text-container-top").css({"display":'none'});
    $(".box-text-container-bottom").css({"display":'none'});
  });

  $("#black").click(function(){
    $(".image").css({"filter":"grayscale(100%)"});
    $(".image").css({"-webkit-filter":"grayscale(100%)"});
  });

  $("#original").click(function(){
    $(".image").css({"filter":"none"});
    $(".image").css({"-webkit-filter":"none"});
  });

  $("#download").click(function(){
    //descargar();
    save();
    //var text = <?php echo json_encode($_SESSION['elementosGuardados']); ?>;
    var text = getSaveElements();
    if(text == "null"){
        alert("No hay datos para guardar");
    }else{
        //var json = JSON.stringify(text);
        //var file = new Blob([json], {type: 'octet/stream'});
        var file = new Blob([text], {type: 'octet/stream'});
        //console.log(json);
        /*if (window.navigator.msSaveOrOpenBlob) // IE10+
            window.navigator.msSaveOrOpenBlob(file, filename);*/
        var link = document.getElementById("downloadLink");
        link.href = URL.createObjectURL(file);
        var date = moment().format('YYYY-M-D - H-m-s');
        link.download = 'pictar_'+date+'.txt';
    }
    
    
    
  });
  /*$("#salvar").click(function(){
    save();
    alert("Datos guardados");
  });*/

 });


/*setInterval(function() {
    var refresh = <?php echo json_encode($_SESSION['endRefresh']); ?>;
    if(refresh){
        generarGrid(numElementos,numColumnas,false,1);
    }
    refresh = false; 

}, 1000);*/

function check_file(result){
    if((result.indexOf("}")== -1)|| (result.indexOf("{")== -1)||(result.indexOf("[")== -1)||(result.indexOf("]")== -1)){
        return [false,0];
    }else{
        result = result.split("}");
        for(i=0; i<result.length-1; i++){
            result[i] = result[i].substring(2, result[i].length);
            result_aux = result[i].split(",");
            if(result_aux.length!=4){
                return [false,0];
            }
        }
        return [true,result.length];
    }
}

function load_file(result,elements){
    var parametro = {
        "import_result": result
    };
    $.ajax({
            data: parametro,
            url: 'generateFile.php',
            type: 'post',
            success: function (response) {
                //alert(response);
                generarGrid(elements,5,false,1);

            }
    });
    
}

function readFile(file, callback){
    var reader = new FileReader();
    reader.onload = callback;
    reader.readAsText(file);
}

function save(){
    var resultado = [];
    var idPadre;
    var valueTopText;
    var url;
    var valueBottomText;
    $('div.grid-container-editor>div>div').each(function(){ 
        if($(this).attr('class')=="box-text-container-top"){
            idPadre = $(this).parent().attr('id').split("-")[1];
            valueTopText = $(this).children().get(0).value;
        }
        //--- ui-droppable
        if($(this).attr('class')=="box-grid-container ui-droppable"){
            url = $(this).children().attr('src');
        }
        if($(this).attr('class')=="box-text-container-bottom"){
            valueBottomText = $(this).children().get(0).value;
            resultado.push({idPadre,valueTopText,url,valueBottomText});
        }
     });
  
    var parametro = {
        "save": resultado
    };
    $.ajax({
            data: parametro,
            url: 'generateGrid.php',
            type: 'post',
            success: function (response) {
                console.log("Guardado");
            }
    });
}

function traducir(){
    $("#translate").attr("disabled","true"); 
    translatorWidth = gridTranslator.clientWidth;
    translatorBox = translatorWidth/(5*1.3);
    newHeightTranslatorBox = 170-(180 - (translatorBox*1.1));
    $("#grid-translator").empty();
    $(".hide-arrow-icon").css({"display":'none'});
    var parametros = {
            "texto" : $("#textToTranslate").val(),
            "box": translatorBox,
            "heightBox": newHeightTranslatorBox
    };
    $.ajax({
            data:  parametros,
            url:   'translator.php',
            type:  'post',
            success:  function (response) {
                if(response == "empty"){
                    alert("Introduzca una palabra o una frase para traducir");
                }else{
                    $("#grid-translator").append(response);
                    $(".hide-arrow-icon").css({"display":'block'});
                }
                $("#translate").removeAttr("disabled"); 
            }
    });
}

function getSaveElements(){
    var parametro = {"getSaveElements": true};
    var resultado = $.ajax({
            data:  parametro,
            url:   'generateGrid.php',
            type:  'post',
            async: false, 
            success:  function (response) {
            }
    }).responseText;

    return resultado;
}

function getNumElementsGridEditor(){
    var parametro = {"getElements": true};
    //var resultado = <?php echo json_encode($_SESSION['numElementos']); ?>;
    var resultado = $.ajax({
            data:  parametro,
            url:   'generateGrid.php',
            type:  'post',
            async: false, 
            success:  function (response) {
            }
    }).responseText;

    return resultado;
}


function getNumColumnsGridEditor(){
    var parametro = {"getColumns": true};
    //var resultado = <?php echo json_encode($_SESSION['numElementos']); ?>;
    var resultado = $.ajax({
            data:  parametro,
            url:   'generateGrid.php',
            type:  'post',
            async: false, 
            success:  function (response) {
            }
    }).responseText;

    return resultado;
}

function generarGrid(elementos,columnas,clickOn,refresh){
    editorWidth = grid.clientWidth;
    box = editorWidth/(columnas*1.3);
    textBox = editorWidth/(columnas*1.3);

    $("#grid-editor").empty();
    var parametros = {
            "numElementos" : elementos,
            "numColumnas" : columnas,
            "box" : box,
            "textBox" : textBox,
            "click" : clickOn,
            "refresh": refresh
    };
    $.ajax({
            data:  parametros,
            url:   'generateGrid.php',
            type:  'post',
            success:  function (response) {
                $("#grid-editor").append(response);
            }
    });

    //------------------------------------------------

    $(".text-field-button").css({"display":'block'});

    //------------------------------------------------
}



function buscar(){
    seekerWidth = gridSeeker.clientWidth;
    searchBox = seekerWidth/(2*1.3);
    newHeightSearchBox = 170-(180 - (searchBox*1.1));
    $("#grid-seeker").empty();
    var parametro = {
        "busqueda": $("#wordToSearch").val(),
        "box": searchBox,
        "heightBox": newHeightSearchBox
    };
    $.ajax({
            data: parametro,
            url: 'searchText.php',
            type: 'post',
            success: function (response) {
                $("#grid-seeker").append(response);
            }
    });
  }

function addNewBox(numColumnas){
    var i = 1;
    var top_aux;
    var bottom_aux;
    var filter_aux;
    $(".grid-container-editor div").each(function() {
        if (document.getElementById("box-" + i)){
          i = i+1;
        }
    });
    
    columnas = numColumnas;
    editorWidth= grid.clientWidth;
    box = editorWidth/(columnas*1.3);
    textBox = editorWidth/(columnas*1.3);

    if (document.getElementById("box-1")){
          top_aux = $(".box-text-container-top").css("display");
          bottom_aux = $(".box-text-container-bottom").css("display");
          filter_aux = $(".image").css("filter");
    }

    var parametros = {
        "index": i,
        "box" : box,
        "textBox" : textBox,
        "top" : top_aux,
        "bottom": bottom_aux,
        "filter": filter_aux
    };
    $.ajax({ url: 'generateGrid.php',
         data: parametros,
         type: 'post',
         success: function (response) {
                $(".grid-container-editor").append(response);
            }
    });

    save();
    document.getElementById("numElements").value = parseInt(document.getElementById("numElements").value)+1;
    
}

function justNumbers(e){
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 8) || (keynum == 46))
    return true;
     
    return /\d/.test(String.fromCharCode(keynum));
}

function dropEvent(ev, ui){
    if(ev.attr("id")!="box-S"){
        var dir = ui.draggable.attr("src");
        var box = ev.children("img").attr("id");
        document.getElementById(box).src = dir;
    }
}

function clean(ev){
    parent = ev.parentElement.id;
    parent = parent.split("-")[2];
    document.getElementById("image-" +parent).src = "./blanco.png";
    save();
}

function remove(ev){
    parent = ev.parentElement.id;
    parent = parent.split("-")[2];
    document.getElementById("box-"+parent).remove();
    
    var numElementos = getNumElementsGridEditor();

    var contador =1;
    $(".grid-container-editor div").each(function() {
        if(contador>parent && contador <=numElementos){
            var aux = document.getElementById("box-" +contador.toString()).children;
            var i;
            for(i=0;i<aux.length;i++){
                if(aux[i].getAttribute('id')=="box-text-top-" + (contador).toString()){
                    document.getElementById("box-text-top-" + contador.toString()).id = "box-text-top-" + (contador-1).toString();
                }else if(aux[i].getAttribute('id')=="box-image" + (contador).toString()){
                    var aux1 = document.getElementById("box-image" +contador.toString()).children;
                    var j;
                    for(j=0;j<aux1.length;j++){
                        if(aux1[j].getAttribute('id')=="image-"+(contador).toString()){
                            document.getElementById("image-" + contador.toString()).id = "image-" + (contador-1).toString();
                        }else if(aux1[j].getAttribute('id')=="clean-remove-"+(contador).toString()){
                            document.getElementById("clean-remove-" + contador.toString()).id = "clean-remove-" + (contador-1).toString();
                        }
                    }
                    document.getElementById("box-image" + contador.toString()).id = "box-image" + (contador-1).toString();
                }else if(aux[i].getAttribute('id')=="box-text-bottom-" + (contador).toString()){
                    document.getElementById("box-text-bottom-" + contador.toString()).id = "box-text-bottom-" + (contador-1).toString();
                }
            }
            document.getElementById("box-" + contador.toString()).id = "box-"+(contador-1).toString();

        }
        contador++;
    });
    var parametro = {
        "remove": true
    };
    $.ajax({
            data: parametro,
            url: 'generateGrid.php',
            type: 'post',
            success: function (response) {
            }
    });
    save();
    document.getElementById("numElements").value = parseInt(document.getElementById("numElements").value)-1;
}

function passImageUp(ev,urls){
    var parent = ev.parentElement.id;
    parent = parent.split("-")[2];
    urls_aux = urls.split(",");
    
    var i = getIndex(parent,false);
    document.getElementById("translator-image-" +parent).src = "http://hypatia.fdi.ucm.es/conversor/Pictos/"+urls_aux[i];
}

function passImageDown(ev,urls){
    var parent = ev.parentElement.id;
    parent = parent.split("-")[2];
    urls_aux = urls.split(",");
    
    var i = getIndex(parent,true);
    document.getElementById("translator-image-" +parent).src = "http://hypatia.fdi.ucm.es/conversor/Pictos/"+urls_aux[i];
}

function getIndex(parent,upDown){
    url = document.getElementById("translator-image-" +parent).src;
    url = url.split("/")[5];
    var i=0;
    var encontrado = false;
    while(i<urls_aux.length && !encontrado){
        if(url == urls_aux[i]){
            encontrado = true;
            indice = i;
        }else{
            i++;
        }
    }
    if(!upDown){
        if(i==0){
            i = urls_aux.length-1;
        }else{
            i = i-1;
        }
    }else{
        i = (i+1)%urls_aux.length;
    }
    return i;
}

function wait(){
    setTimeout(save,1000);
}
</script>