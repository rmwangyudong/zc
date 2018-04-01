package com.wyd.zc.demo.web;

import com.wyd.zc.demo.core.Result;
import com.wyd.zc.demo.core.ResultGenerator;
import com.wyd.zc.db.model.ZcUserUsers;
import com.wyd.zc.demo.service.ZcUserUsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 王玉栋
 * @date 2018-04-01 21:22:33
 * @since 1.0
 */
@RestController
@RequestMapping("/zc/user/users")
public class ZcUserUsersController {

    @Resource
    private ZcUserUsersService zcUserUsersService;

    @PostMapping
    public Result add(@RequestBody ZcUserUsers zcUserUsers) {
        zcUserUsersService.save(zcUserUsers);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Object id) {
        zcUserUsersService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ZcUserUsers zcUserUsers) {
        zcUserUsersService.update(zcUserUsers);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Object id) {
        ZcUserUsers zcUserUsers = zcUserUsersService.findById(id);
        return ResultGenerator.genSuccessResult(zcUserUsers);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ZcUserUsers> list = zcUserUsersService.findAll();
        PageInfo<ZcUserUsers> pageInfo = new PageInfo<ZcUserUsers>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
