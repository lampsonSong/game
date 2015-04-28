        var run=false;
        var tid;
        var last, end, start;
        var times=new Array();
        var count=0;
        var complete_count = 0;
        
        
        var word = ["红色","橙色","黄色","绿色","青色","蓝色","紫色","黑色","白色"];
        var color = ["red","orange","yellow","green","cyan","blue","purple","black","white"];
        var value = [0,1,2,3,4,5,6,7,8];
        
        
        function init()
        {
           
       	    document.getElementById("frame_div_rule").style.display = "block";
            document.getElementById("frame_div_game").style.display = "none";
            document.getElementById("frame_div_fail").style.display = "none";
            document.getElementById("frame_div_success").style.display = "none";
            document.getElementById("frame_div_giveup").style.display = "none";
            document.getElementById("time").style.display = "none";
            changeMap();
        }
        
        //乱序排列
        function shuffle(v)
        { 
			for(var j, x, i = v.length; i; j = parseInt(Math.random() * i), x = v[--i], v[i] = v[j], v[j] = x); 
			return v; 
		}
        
        //生成随机数
        function random(min,max)
        {
    		return Math.floor(min+Math.random()*(max-min));
		}
        
        
        
        //匹配
        function match(rand_num,word_color)
        {
		if(run)
            {
                if(complete_count < 5)
                {
                    if(rand_num == word_color)
                    {   
                        completecountAdd();                                      
                        //重新载入文字和颜色
                        changeMap();
                    }
                    else//没有做够6道，就错了
                    {   
                        stoptimer();
                        fail();
                        //在页面显示只做了几道
                        
                    }
                }
                else
                {
                    //complete
                    stoptimer();
                    success();
                    
                    //显示已经完成
                }
            }
        }
        
        
        //更改游戏界面
        function changeMap()
        {
			var word_meaning = random(0,9);
        	var word_color = random(0,9);
            
			var first;
			var second;
			var third;
			
            document.getElementById("target_word").innerHTML =  word[word_meaning];
            document.getElementById("target_word").style.color = color[word_color];
			
            shuffle(value);
            
	    for(var i = 0; i < 9; i++)
            {
				var block_id = "block"+i;
				
				first = (0+i)%9;
				second = (1+i)%9;
				third = (2+i)%9;
                    document.getElementById(block_id).style.background = color[value[first]];
                    document.getElementById(block_id).style.color = color[value[second]];
                    document.getElementById(block_id).innerHTML = word[value[third]];
                    document.getElementById(block_id).setAttribute("onclick","match("+value[third]+","+word_color+")");
            }       
                    
                   
        }
        
        function switcher()
        {
            //初始状态为“开始游戏”，点击之后变成“放弃游戏”
            if(run==false)
            {
                changeMap();
                //打开计时器
                starttimer();
                //修改按钮为“放弃游戏”，跳转到游戏页面
                document.getElementById("switcher_button").value = "放弃游戏";
		document.getElementById("frame_div_giveup").style.display = "none";
		document.getElementById("frame_div_success").style.display = "none";
		document.getElementById("frame_div_fail").style.display = "none";
            	
		document.getElementById("frame_div_rule").style.display = "none";
            	document.getElementById("frame_div_game").style.display = "block";
            	document.getElementById("time").style.display = "block";
            }
            else if(run==true)
            {
                if(document.getElementById("switcher_button").value == "重新开始")
                {
                    window.location.reload();
                    stoptimer();
                    document.getElementById("switcher_button").value = "放弃游戏";
                    //document.frame.location.href = "giveup.php";
			document.getElementById("frame_div_game").style.display = "block";
                }
                else//放弃游戏
                {
                    stoptimer();
                    document.getElementById("switcher_button").value = "重新开始";
                    //document.frame.location.href = "giveup.php";
                    //document.frame.location.replace = "giveup.php";
			document.getElementById("frame_div_game").style.display = "none";
			document.getElementById("frame_div_giveup").style.display = "block";
			document.getElementById("time").style.display = "none";
                }
            }
        }
		
        
        function success()
        {
            complete_count = 0;
            document.getElementById("switcher_button").value = "重新开始";
			//隐藏游戏div,显示成功div
            	document.getElementById("frame_div_game").style.display = "none";
            	document.getElementById("frame_div_success").style.display = "block";
            	document.getElementById("time").style.display = "none";
            	document.getElementById("success_time").innerHTML = roundNumber(last, 3);
        }
        function fail()
        {
            complete_count = 0;
            document.getElementById("switcher_button").value = "重新开始";
			//隐藏游戏div，显示失败div
            	document.getElementById("frame_div_game").style.display = "none";
            	document.getElementById("frame_div_fail").style.display = "block";
            	document.getElementById("time").style.display = "none";
            	document.getElementById("fail_time").innerHTML = roundNumber(last, 3);
            	document.getElementById("fail_count").innerHTML = count;

        }
        
        function completecountAdd()
        {
            complete_count++;
        }
            
        function starttimer()
        {
            run=true;
            start=new Date();
            tid=setInterval(printtime, 10);
        }
        function stoptimer()
        {
            run=false;
            clearInterval(tid);
            times[count++]=last;
            //printstats();
        }
         
        function printtime()
        {
            end=new Date();
            last=(end-start)/1000;
            document.getElementById("time").innerHTML=roundNumber(last, 3);
        }
        
        function roundNumber(num, dec)
        {
            var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
            return result;
        }
