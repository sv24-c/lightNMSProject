package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardBean
{
    private int upCount = 0;

    private int downCount = 0;

    private int unknownCount = 0;

    private List<Integer> dashboardList = new ArrayList<>();

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public int getDownCount() {
        return downCount;
    }

    public void setDownCount(int downCount) {
        this.downCount = downCount;
    }

    public int getUnknownCount() {
        return unknownCount;
    }

    public void setUnknownCount(int unknownCount) {
        this.unknownCount = unknownCount;
    }

    public List<Integer> getDashboardList() {
        return dashboardList;
    }

    public void setDashboardList(List<Integer> dashboardList) {
        this.dashboardList = dashboardList;
    }
}
