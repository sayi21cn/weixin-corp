weixin-corp
===========

基于jfinal的企业微信快速开发平台，功能描述....

  a.api:基本操作接口
  b.msg:交互消息对象化
  c.kit:加解密工具包
  （微信内部使用的加密算法和给开发这提供的不一致decode(encode(a))<>a，做了修正，方便测试）   
    
1.如何初始化

	ApiConfig.setAppId("wxb21adacab9c87409");
	ApiConfig.setAppSecret("22ix3QfnYy690tvDdsJN7NkoEhj485X98H9Gpn8grU7VUxLOsFaAOGPrx9gTPF0s");
	ApiConfig.setEncodingAESKey("vuSWZYdBrwWqeFEHZQVM6kFngXaz6AhRXuTwO1b1MCH");
	ApiConfig.setAgentId("1");
	ApiConfig.setToken("xad1");
		
2.如何验证echostr

	 boolean isOk = SignatureCheckKit.me.checkSignature(msg_signature, timestamp, nonce,echostr);
	if (isOk){
		WxCryptUtil pc = getWxCryptUtil();
		echostr= pc.decrypt(echostr);
		System.out.println("验证成功，解密后的echostr:"+echostr);
	}else
	System.out.println("验证失败！");
	
3.如何接收消息

    String xml="<xml><Encrypt><![CDATA[mCi6L35PDb2EOIfQdWN1KyTAbg2EaZikBg96umG/Og0fagBzgoa7k+qGpI72FLg2BZt+hnaLTV1Y7jpZXRqqJR4YFmKQ7B00AbFuRHt+64Ds7xODi1mmhLp8CKHSqYl25ujoM9BMnChpJAbGwRdDPKc+Y4F2qX7kbwEsczWElQHmfkv2iM12AScoDLZzR8eH2/CfB9KKjPsIJ+dd017OrhQjIK1uMZ6IoND7+sJSE13N6ZRbrD4pj+yg36IJ5m/K7+vqHKJkTZ7YFnkYDhDtQjUbsKu1w+rY4AFSrRjWjFLMtHXsVY/Bn7nt9/uUemplppMoNvXRj6dZesu6fqi/FLqcHabXqycDS48QsJs3neKxQ6+Kw8WtDGqtWWVGjIXyQ2GICVYsUshoBcRVAXhLcXEou7ZHnFPSOcH8lR4lJPEMKQ9oi69JXt1pAObxqw1X8CDq2YxmxP4oqHfSuzOcyBJ94iV3JfOvZQLW3RqpmDDo9aTw67z2xy7X7rShxfHb]]></Encrypt><MsgSignature><![CDATA[f0a674c0cdcb5bfa42bbbe019c388ca0628980e7]]></MsgSignature><TimeStamp>1416467263</TimeStamp><Nonce><![CDATA[xmP0FxUsgjxiCKkh]]></Nonce></xml>";
	WxCryptUtil pc = getWxCryptUtil();
	
	//解密接收消息
	xml= pc.decrypt(msg_signature, timestamp, nonce, xml);
	InMsg inMsg = InMsgParaser.parse(xml); 
	
	OutTextMsg outMsg= new OutTextMsg(inMsg);
	outMsg.setContent("收到");
	
	//加密返回消息
	String outMsgXml = OutMsgXmlBuilder.build(outMsg);
	String destMsgXml= pc.encrypt(outMsgXml);
	
	System.out.println(destMsgXml);

4.如何主动发送消息

	JsonTextMsg msg=new JsonTextMsg();
	msg.agentid=ApiConfig.getAgentId();
	msg.touser="15102828125";
	msg.content="hello";
	
	ApiResult ret =MessageApi.sendMsg(msg);
	System.out.println(ret.getJson());

5.更多...

     
