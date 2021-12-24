package org.jeecg.modules.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.entity.Fortune;
import org.jeecg.modules.demo.service.IFortuneService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 运程
 * @Author: jeecg-boot
 * @Date:   2021-12-16
 * @Version: V1.0
 */
@Api(tags="运程")
@RestController
@RequestMapping("/demo/fortune")
@Slf4j
public class FortuneController extends JeecgController<Fortune, IFortuneService> {
	@Autowired
	private IFortuneService fortuneService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fortune
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "运程-分页列表查询")
	@ApiOperation(value="运程-分页列表查询", notes="运程-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Fortune fortune,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Fortune> queryWrapper = QueryGenerator.initQueryWrapper(fortune, req.getParameterMap());
		Page<Fortune> page = new Page<Fortune>(pageNo, pageSize);
		IPage<Fortune> pageList = fortuneService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fortune
	 * @return
	 */
	@AutoLog(value = "运程-添加")
	@ApiOperation(value="运程-添加", notes="运程-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Fortune fortune) {
		if (fortuneService.save(fortune)) {
			return Result.OK(Math.random());
		} else {
			return Result.error("失败");
		}
	}
	
	/**
	 *  编辑
	 *
	 * @param fortune
	 * @return
	 */
	@AutoLog(value = "运程-编辑")
	@ApiOperation(value="运程-编辑", notes="运程-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Fortune fortune) {
		fortuneService.updateById(fortune);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "运程-通过id删除")
	@ApiOperation(value="运程-通过id删除", notes="运程-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fortuneService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "运程-批量删除")
	@ApiOperation(value="运程-批量删除", notes="运程-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fortuneService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "运程-通过id查询")
	@ApiOperation(value="运程-通过id查询", notes="运程-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Fortune fortune = fortuneService.getById(id);
		if(fortune==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(fortune);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fortune
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Fortune fortune) {
        return super.exportXls(request, fortune, Fortune.class, "运程");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Fortune.class);
    }

}
