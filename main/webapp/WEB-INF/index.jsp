<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Welcome to LNMS</title>

    <link rel="stylesheet" href="/css/vertical-layout-light/style.css">
    <link rel="stylesheet" href="/vendors/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="/css/index-main-style.css">
    <link rel="stylesheet" href="/css/toastr.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">

    <script type="text/javascript" src="/js/jQueryFile.js"></script>
    <script type="text/javascript" src="/js/loginPage.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
    <script type="text/javascript" src="/js/discovery.js"></script>
    <script type="text/javascript" src="/js/mainDashboard.js"></script>
    <script type="text/javascript" src="/js/monitor.js"></script>
    <script type="text/javascript" src="/js/toastr.min.js"></script>
    <script src="/js/websocket.js"></script>

    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"> </script>

    <script>

        $(document).ready(function () {

            dashboardmain.onload();

            discoverymain.onload();

            monitormain.onload();

        });

    </script>

</head>
<body>

<div class="container-scroller">
    <nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex align-items-top flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-start">
            <div>
                <p><h4 class="text-black fw-bold">Light NMS</h4></p>
            </div>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-top">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="dropdown-item" href="logoutProcess" id="logout"><i class="dropdown-item-icon mdi mdi-power text-primary me-2"></i>Log Out</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container-fluid page-body-wrapper">
        <nav class="sidebar sidebar-offcanvas" id="sidebar">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" id="dashboard" onclick="dashboardmain.onload()"<%--href="dashboardClick"--%>>
                        <i class="mdi mdi-grid-large menu-icon"></i>
                            <span class="menu-title">Dashboard</span>
                    </a>
                </li>
                <li class="nav-item nav-category">UI Elements</li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
                        <i class="menu-icon mdi mdi-floor-plan"></i>
                        <span class="menu-title">UI Elements</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="ui-basic">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item" id="discovery-onclick"> <a class="nav-link" <%--onclick="discoverymain.onload()"--%>>Discovery</a></li>
                            <li class="nav-item" id="monitor-onclick"> <a class="nav-link" > <%--href="pages/ui-features/dropdowns.html"--%>Monitor</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </nav>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="home-tab">
                            <div class="tab-content tab-content-basic">
                                <div class="tab-pane fade show active" id="overview" role="tabpanel" aria-labelledby="overview">


                                    <div class="col-lg-12 grid-margin stretch-card">
                                        <div class="card"> <div class="card-body"> <h4 class="card-title">Dashboard</h4>
                                            <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px">
                                                <div id="upCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Up<hr><p style="font-size: 25px"></p></div>
                                                <div id="downCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Down<hr><p style="font-size: 25px"></p> </div>
                                                <div id="unknownCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Unknown<hr><p style="font-size: 25px"></p> </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                </div>
            </footer>
        </div>
    </div>
</div>

</div>

<script src="/vendors/js/vendor.bundle.base.js"></script>
<script src="/vendors/chart.js/Chart.min.js"></script>
<script src="/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
</body>
</html>
