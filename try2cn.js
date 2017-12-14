

function isNull(data){ 
return (data == "" || data == undefined || data == null) ? true : false; 
}


function RepItemSkillTree(link,linkCN){

 var skill=document.getElementsByClassName('characterPassiveSkillTree');
 if(typeof(skill)=='undefined'){
	 return;
 }
var iframe=skill[0].getElementsByTagName('iframe')[0];
 if(typeof(iframe)=='undefined'){
	 return;
 }
var tmp_linkCN=linkCN;
var itemBoxContent=iframe.contentWindow.document.getElementsByClassName('itemBoxContent');

for(var i = 0; i < itemBoxContent.length; i++){
    

var group = link.exec(itemBoxContent[i].innerHTML); 


if(isNull(group)!=true){ 

linkCN=tmp_linkCN;
for(var j=1;j<group.length;j=j+2){
	if(typeof(group[j])!='undefined'){
linkCN=linkCN.replace('#',group[j]);
}
}


itemBoxContent[i].innerHTML=itemBoxContent[i].innerHTML.replace(group[0],linkCN);
group=[];

}

}
linkCN='';

}


function RepItem(link,linkCN){

var tmp_linkCN=linkCN;
var itemBoxContent=document.getElementsByClassName('itemBoxContent')
for(var i = 0; i < itemBoxContent.length; i++){
             
var group = link.exec(itemBoxContent[i].innerHTML); 
if(isNull(group)!=true){ 
 
linkCN=tmp_linkCN;
for(var j=1;j<group.length;j=j+2){
if(typeof(group[j])!='undefined'){
linkCN=linkCN.replace('#',group[j]);
}


}

itemBoxContent[i].innerHTML=itemBoxContent[i].innerHTML.replace(group[0],linkCN);
group=[];

}

}
linkCN='';

}





for(var trynum=0;trynum<3;trynum++){

document.title='第'+(trynum+1)+'遍正在处理';

	for(var i=0;i<modsjson.length;i++){
	link=modsjson[i].en;
	linkCN=modsjson[i].cn; 
	 //遍历珠宝in论坛 优先加载处理后的珠宝词缀进行
	if(document.location.href.indexOf('account/view-profile')==-1){
	//论坛用普通遍历
		RepItem(link,linkCN);
	}else{
	RepItem(link,linkCN);
	//否则要iframe。  处理后的珠宝词缀
		RepItemSkillTree(link,linkCN);
	}
	}

for(var i=0;i<itemjson.length;i++){

	link=itemjson[i].en;
	linkCN=itemjson[i].cn; 
	RepItem(link,linkCN);

  }

  for(var i=0;i<ExplicitModjson.length;i++){

	link=ExplicitModjson[i].en;
	linkCN=ExplicitModjson[i].cn; 
	
	if(document.location.href.indexOf('account/view-profile')==-1){
	 RepItem(link,linkCN);
	//当前词缀进行珠宝 默认就是了

	}else{
	//否则要iframe
	RepItem(link,linkCN);
	RepItemSkillTree(link,linkCN);
	}

  }

 for(var i=0;i<gemJson.length;i++){

	link=gemJson[i].en;
	linkCN=gemJson[i].cn; 
	RepItem(link,linkCN);

  }
 for(var i=0;i<uniqueNameJson.length;i++){

	link=uniqueNameJson[i].en;
	linkCN=uniqueNameJson[i].cn; 
	RepItem(link,linkCN);

  }
for(var i=0;i<baseItemJson.length;i++){

	link=baseItemJson[i].en;
	linkCN=baseItemJson[i].cn; 
	RepItem(link,linkCN);

  }

  if(document.location.href.indexOf('account/view-profile')!=-1){
	for(var i=0;i<baseJewelJson.length;i++){

	link=baseJewelJson[i].en;
	linkCN=baseJewelJson[i].cn; 
	RepItemSkillTree(link,linkCN);

  }
  }else{
	  for(var i=0;i<baseJewelJson.length;i++){

		link=baseJewelJson[i].en;
		linkCN=baseJewelJson[i].cn; 
		RepItem(link,linkCN);

	  }
  }
for(var i=0;i<baseItemTypeJson.length;i++){

	link=baseItemTypeJson[i].en;
	linkCN=baseItemTypeJson[i].cn; 
	RepItem(link,linkCN);

  }
  }


document.title='处理完成';

alert('完成');