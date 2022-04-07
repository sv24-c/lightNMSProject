package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardBean
{

    private List<Integer> dashboardList = new ArrayList<>();

    public List<Integer> getDashboardList() {
        return dashboardList;
    }

    public void setDashboardList(List<Integer> dashboardList) {
        this.dashboardList = dashboardList;
    }
}
