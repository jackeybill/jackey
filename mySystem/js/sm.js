var $ = lib.c.$;

var relationService = $("#relationService");
var erddefService = $("#erddefService");
var dbdictService = $("#relationManagerDbdictService");


var XueJunService = $(lib.DbdictService.getClass(), new function() {

});

function getClass() {
	return XueJunService;
}


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