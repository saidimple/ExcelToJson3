console.log("working");
var uploadFile = function() {
	let formData = new FormData(); 
    formData.append("file", document.querySelector("input[type='file']").files[0]);
    
	var XMLreq = new XMLHttpRequest();
	XMLreq.onreadystatechange = makeResponse;
	XMLreq.open("POST","getJsonData");
	XMLreq.send(formData);
}
var makeResponse = function()
{
	if(this.readyState==4)
	{
		var parsedData = JSON.parse(this.responseText);
		var heading = "<tr>";
		var values = "";
		var keyData = Object.keys(parsedData[Object.keys(parsedData)[0]][0]);
		var arrName = Object.keys(parsedData)[0];
		for (var head in keyData) {
			heading += "<th>" + keyData[head] + "</th>";
		}
		heading += "</tr>";
		//console.log(parsedData[arrName]);
		for (var j = 0; j < parsedData[arrName].length; j++) {
			var valueArr = Object.values(parsedData[arrName][j]);
			console.log(valueArr);
			values += "<tr>";
			for (var head in valueArr) {
				values += "<td>" + valueArr[head] + "</td>";
				console.log(head);
			}
			values += "</tr>";
		}
		console.log(values);
		document.getElementById("tableData").innerHTML += heading;
		document.getElementById("tableData").innerHTML += values;
	}
}
document.getElementById("uploadButton").addEventListener("click",uploadFile);