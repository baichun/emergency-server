package com.comtom.bc.server.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.EbmBrdLogService;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrTerminalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "终端信息接口")
@RestController
@RequestMapping("/rest/terminal")
public class TerminalRest extends BaseRest {
	
	@Resource
	private EbmBrdLogService ebmBrdLogService;
	@Resource
	private EbrTerminalService ebrTerminalService;
	@Resource
	private EbrBsService ebrBsService;
	
	@Resource
	private RestTemplate restTemplate;

	/**
	 * 获取广播状态
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "info/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取终端信息", notes = "获取终端信息")
	@ApiImplicitParam(name = "id", value = "终端ID", dataType = "string", paramType = "path")
	public ResponseEntity<String> obtainState(HttpServletRequest request, @PathVariable("id") String id) {
		EbrTerminal terminal = ebrTerminalService.findByPK(id);
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> newJsonMap = WebCxt.newJsonMap(resultJson);
		newJsonMap.put("terminal", terminal);
		if(terminal!=null) {
			resultJson.setTotalCount(1);
		}
		return ResponseEntity.ok(JsonUtil.toJson(resultJson));
	}
	@GetMapping("listByAreCode/{areaCode}")
	//@GetMapping(value = "info/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> obtainTerminalByAreaCode(HttpServletRequest request, @PathVariable("areaCode") String areaCode){
		if(CommonUtil.isNotEmpty(areaCode)) {
			List<EbrTerminal> terminals = ebrTerminalService.findByAreaCode(areaCode);
			JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
			Map<String, Object> newJsonMap = WebCxt.newJsonMap(resultJson);
			newJsonMap.put("terminals", terminals);
			resultJson.setTotalCount(terminals.size());
			return ResponseEntity.ok(JsonUtil.toJson(resultJson));
		}
//		String tokenId="THIRDPART_20171219qO3gUV";
//		String secretKey="KEY_Mjw5XDh4";
//		String relatedPlatformId = "";
//		List<EbrBroadcast> broadcasts = ebrBsService.findByRelatedPsEbrId(relatedPlatformId);
//		String uri="http://192.168.100.199/ts/api/term.do?method=getTermsByAreaCode&tokenId={tokenId}&secretKey={secretKey}&areaCode={areaCode}";
////		Map<String, String> uriVariables = new HashMap<>();
////		uriVariables.put("tokenId", tokenId);
////		uriVariables.put("secretKey", secretKey);
////		uriVariables.put("areaCode", areaCode);
//		ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class, tokenId,secretKey,areaCode);
//		String body = forEntity.getBody();
//		System.out.println(body);
		return ResponseEntity.ok().build();
	}
	
	
}
