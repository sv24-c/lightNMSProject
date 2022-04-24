package bean;

import java.util.*;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardBean
{

    private List<HashMap<String, Object>> dashboardList = new ArrayList<>();

    private List<HashMap<String, Object>> topFiveCpuData = new ArrayList<>();

    public List<HashMap<String, Object>> getDashboardList() {
        return dashboardList;
    }

    public void setDashboardList(List<HashMap<String, Object>> dashboardList) {
        this.dashboardList = dashboardList;
    }

    public List<HashMap<String, Object>> getTopFiveCpuData() {
        return topFiveCpuData;
    }

    public void setTopFiveCpuData(List<HashMap<String, Object>> topFiveCpuData) {
        this.topFiveCpuData = topFiveCpuData;
    }
}
