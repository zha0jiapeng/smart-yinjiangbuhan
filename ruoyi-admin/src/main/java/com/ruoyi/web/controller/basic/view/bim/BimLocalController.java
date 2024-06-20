package com.ruoyi.web.controller.basic.view.bim;

import com.ruoyi.system.domain.bim.*;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("bim/local")
public class BimLocalController {
    /**
     * 人员定位
     *
     * @return 结果
     */
    @PostMapping("/coordinate")
    public Result<?> coordinate() {
        BimLocalDomain bimLocalDomain = new BimLocalDomain();

        List<BimLocalDomain.Coordinate> coordinateList = new ArrayList<>();

        BimLocalDomain.Coordinate coordinate = new BimLocalDomain.Coordinate();
        coordinate.setId("1");
        coordinate.setName("测试点位名称");
        coordinate.setDepartment("测试部门");
        coordinate.setTelephone("13718992906");
        coordinate.setX("36");
        coordinate.setY("89");
        coordinate.setZ("97");

        coordinateList.add(coordinate);

        bimLocalDomain.setArray(coordinateList);

        return Result.OK(bimLocalDomain);
    }

    /**
     * 应急事故柜
     *
     * @return 结果
     */
    @PostMapping("/meet/need")
    public Result<?> meetNeed() {
        BimMeetNeedDomain bimMeetNeedDomain = new BimMeetNeedDomain();

        List<BimMeetNeedDomain.Malfunction> array = new ArrayList<>();

        BimMeetNeedDomain.Malfunction malfunction = new BimMeetNeedDomain.Malfunction();
        malfunction.setId("1");
        malfunction.setPlace("place");
        malfunction.setGoods("goods");
        malfunction.setX("x");
        malfunction.setY("y");
        malfunction.setZ("z");

        array.add(malfunction);

        bimMeetNeedDomain.setArray(array);

        return Result.OK(bimMeetNeedDomain);
    }

    /**
     * 消防站
     *
     * @return 结果
     */
    @PostMapping("/protection")
    public Result<?> protection() {
        BimProtectionDomain bimProtectionDomain = new BimProtectionDomain();

        List<BimProtectionDomain.Site> array = new ArrayList<>();

        BimProtectionDomain.Site site = new BimProtectionDomain.Site();
        site.setId("1");
        site.setPlace("place");
        site.setGoods("goods");
        site.setX("x");
        site.setY("y");
        site.setZ("z");

        array.add(site);

        bimProtectionDomain.setArray(array);

        return Result.OK(bimProtectionDomain);
    }

    /**
     * 物联网测点
     *
     * @return 结果
     */
    @PostMapping("/things")
    public Result<?> things() {
        BimThingsDomain bimThingsDomain = new BimThingsDomain();

        List<BimThingsDomain.Station> array = new ArrayList<>();

        BimThingsDomain.Station station = new BimThingsDomain.Station();
        station.setId("1");
        station.setTypename("typename");
        station.setValue("value");
        station.setState("state");
        station.setX("x");
        station.setY("y");
        station.setZ("z");

        array.add(station);

        bimThingsDomain.setArray(array);

        return Result.OK(bimThingsDomain);
    }

    /**
     * 设备
     *
     * @return 结果
     */
    @PostMapping("/equipment")
    public Result<?> equipment() {
        BimEquipmentDomain bimEquipmentDomain = new BimEquipmentDomain();

        List<BimEquipmentDomain.Details> array = new ArrayList<>();

        BimEquipmentDomain.Details details = new BimEquipmentDomain.Details();
        details.setEquipmentname("equipmentname");
        details.setInformation("information");
        details.setState("state");
        details.setX("x");
        details.setY("y");
        details.setZ("z");

        array.add(details);

        bimEquipmentDomain.setArray(array);
        return Result.OK(bimEquipmentDomain);
    }

    /**
     * 监控
     *
     * @return 结果
     */
    @PostMapping("/control")
    public Result<?> control() {
        BimControlDomain bimControlDomain = new BimControlDomain();

        List<BimControlDomain.Details> array = new ArrayList<>();

        BimControlDomain.Details details = new BimControlDomain.Details();
        details.setId("1");
        details.setUrl("url");
        details.setX("x");
        details.setY("y");
        details.setZ("z");

        array.add(details);

        bimControlDomain.setArray(array);

        return Result.OK(bimControlDomain);
    }

    /**
     * 地质
     *
     * @return 结果
     */
    @PostMapping("/geology")
    public Result<?> geology() {
        BimGeologyDomain bimGeologyDomain = new BimGeologyDomain();

        List<BimGeologyDomain.Details> array = new ArrayList<>();

        BimGeologyDomain.Details details = new BimGeologyDomain.Details();
        details.setId("id");

        BimGeologyDomain.ProspectInfo prospectInfo = new BimGeologyDomain.ProspectInfo();
        prospectInfo.setName("name");
        prospectInfo.setState("state");
        prospectInfo.setInfo("info");
        details.setProspectInfo(prospectInfo);

        BimGeologyDomain.ForecastInfo forecastInfo = new BimGeologyDomain.ForecastInfo();
        forecastInfo.setName("name");
        forecastInfo.setState("state");
        forecastInfo.setInfo("info");
        details.setForecastInfo(forecastInfo);

        BimGeologyDomain.RealityInfo realityInfo = new BimGeologyDomain.RealityInfo();
        realityInfo.setName("name");
        realityInfo.setState("state");
        realityInfo.setInfo("info");
        details.setRealityInfo(realityInfo);

        array.add(details);

        bimGeologyDomain.setArray(array);

        return Result.OK(bimGeologyDomain);
    }

}