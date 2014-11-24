var $ = lib.c.$;

var relationService = $("#relationService");
var erddefService = $("#erddefService");
var dbdictService = $("#relationManagerDbdictService");


var XueJunService = $(lib.DbdictService.getClass(), new function() {

});

function getClass() {
	return XueJunService;
}

//lib.test。listStructure

function getFieldInfo(){
	var $ = lib.c.$;
	var dbdictService = $("#dbdictService");
	var dbdict = $("category").dbdict();
	var descriptorMeta = dbdictService.getDescriptorMeta(dbdict["field"]);
	var fieldMeta = dbdictService.getFieldMeta("qbe.format", descriptorMeta, false);
	
	print(fieldMeta.path);
	
	var sqlFieldMeta = dbdictService.getFieldMetaWithSQLInfo(fieldMeta);
	print(sqlFieldMeta.field.name);
}

function listStructure(){
	//var aci = lib;
	//var aci = lib.acicategory;
	//var aci = system.functions;
	//var aci = new XML();
	//var aci = new Array();
	//var aci = "sdfsdfsdf";
	//var aci = {};
	//var aci = new SCFile("OOFlow");
	var aci = $("#relationManagerDbdictService");
	print("me");
	for (var name in aci) {
    	print( name );
    	//print( aci[x] );

	}
	// for (var obj in window){window.hasOwnProperty(obj) && typeof window[obj] === 'function')objs.push(obj)};
}

function listStructureOK(){
	var aci = lib.acicategory;
	var aci = "sdfsdfsdf";
	var aci = $("#relationManagerDbdictService");
	for (x in aci) {
    	//txt += person[x];
    	print( x );
    	//print( x["arguments"] );
    	for( y in x ){
    		//print( x[y] );
    	}
    	break;
	}
}


function listJSlib(){
	var str;
	var scriptLibrary = new SCFile("ScriptLibrary");	
	var rc=scriptLibrary.doSelect( true ) ;
	while ( rc == RC_SUCCESS){
		str += " "+scriptLibrary.name;
		rc=scriptLibrary.getNext();	
	}
	print( str );		
}

listStructure();

function testMe(){
	var relation = new SCFile("relation");
	relation["source.file"] = "WorkflowPhase";
	relation["source.field"] = "workflowName|tableName";
	relation["target.file"] = "cm3r";
	relation["target.field"] = "misc.array2";
	relation["relType"] = "AA";	
	
	//relationService.isValidFields('source',relation);
	
	//relationService.checkUniqueKey(relation);
	
	relationService.validate(relation);
	
	print( relation );
}

truncateBydefType : function(defType) {
    //var relations = $("relation").select('def.type="' + defType + '" and status ~= "fixed"').list();
    
    $("relation").select('def.type="' + defType + '" and status ~= "fixed"').iterate(function(relation) {
       relation.doDelete();
    });
},


function testMe2(){
	//erddefService.buildRelations();
	//print( 'filename()|number'.split('|').length );
	//print( 'number'.split('|').length );
	//print( 'number'.split('|')[0] );
	
	//var targetTableDbDict = dbdictService.getValidDbdictWithCacheByName('workflow');
	var targetTableDbDict = dbdictService.getValidDbdictWithCacheByName('cm3r');
	var targetCombinedKeys = dbdictService.getCombinedFieldUniqueKey(targetTableDbDict["key"]);
	
	print( targetCombinedKeys.length );
	
	var array1 = [1,2,3,4,5];
	var array2 = [1,2,3,4,5];
	var array3 = [3,5,1,2,4];
	
	print( array3.join() );
	print( array3.sort().join() );
	print( array1.join() === array2.join() );
	print( array1.sort().join() === array3.sort().join() );
	
	for (var field in ('affected.services|test'.split('|'))) {
				print('field='+field);
			}
}

function testMe1(){
	// This issue also happens to fields ”middle,affected.servces,affected.service”,”middle,misc.array2,misc.array2” in the result of cm3r.
	var relation = new SCFile("relation");
	relation["source.file"] = "cm3r";
	relation["source.field"] = "affected.services";
	relation["target.file"] = "cm3r";
	relation["target.field"] = "misc.array2";
	
	relationService.validate(relation);
	
	print( relation );
	// Attached is the relation manager result of probsummary, the  field affteced.services in dbdict is defined as array and mapped to CLOB.
	relation["target.file"] = "probsummary";
	relation["target.field"] = "affected.services";
	relationService.validate(relation);	
	print( relation );		
}

testMe();

//return true when the requried fields are filled, return false if not.
function checkValidation(requiredList,valueList){
	var i = 0;
	if(requiredList.length()>0){
		while( i in requiredList){
		 	if(requiredList[i] == "true" || requiredList[i] == true){
				if(valueList!=null && i in valueList){
					if(valueList[i] == null || valueList[i] == ""){
						return false;
					}
				}else {
					return false;
				}
			}
			i++;
		}
	}
	return true;
}