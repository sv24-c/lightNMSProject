<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Welcome</title>

    <!-- plugins:css -->
    <link rel="stylesheet" href="/vendors/feather/feather.css">
    <link rel="stylesheet" href="/vendors/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="/vendors/typicons/typicons.css">
    <link rel="stylesheet" href="/vendors/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet" href="/vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <link rel="stylesheet" href="/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <link rel="stylesheet" href="/js/select.dataTables.min.css">
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="/css/vertical-layout-light/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="/images/favicon.png" />

    <link rel="stylesheet" href="/scss/vertical-layout-light/_sidebar.scss">
    <link rel="stylesheet" href="/scss/common/light/_utilities.scss">
    <link rel="stylesheet" href="/scss/common/light/components/plugin-overrides/_colcade.scss">
    <link rel="stylesheet" href="/scss/common/light/components/plugin-overrides/_chartist.scss">
    <link rel="stylesheet" href="/scss/common/light/components/_tabs.scss">
    <link rel="stylesheet" href="/scss/common/light/mixins/_cards.scss">


    <link rel="stylesheet" href="/css/index-main-style.css">


    <%--<script type="text/html" src="/partials/_navbar.html"></script>--%>

    <script src="/js/jQueryFile.js"></script>
    <script type="text/javascript" src="/js/loginPage.js"></script>
    <script src="/js/discoveryjQuery.js"></script>

    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

    <script>

        $(document).ready(function () {

            discoveryVariable.discoveryOnClick();

            discoveryVariable.showMonitor();

            discoveryVariable.showDashboard();

        });

    </script>

</head>
<body  <%--class="sidebar-icon-only"--%>>
<%--<div class="icon-bar">
    <a class="active" ><i class="fa fa-tachometer" aria-hidden="true"> Dashboard</i></a>
    <button id="discovery-a"  class="btn btn-primLogoutary" onclick="discoveryIn()"><i class="fa fa-search"> Discovery</i></button>
&lt;%&ndash;
    <button type="button" id="discovery-a1" class="btn btn-primary" onclick="discoveryIn()">Primary</button>
&ndash;%&gt;
    <a href="#"><i class='fa-thin fa-monitor-waveform'>Monitor</i> </a>
    <a href="#"><i class="fa fa-user" aria-hidden="true"> Profile</i></a>
    <a href="logoutProcess"><i class="fa fa-sign-out" aria-hidden="true"> Logout</i></a>

</div>--%>

<div class="container-scroller">
    <!-- partial:partials/_navbar.html -->
    <nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex align-items-top flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-start">
            <div class="me-3">
                <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-bs-toggle="minimize">
                    <span class="icon-menu"></span>
                </button>
            </div>
            <div>
                <%--<a class="navbar-brand brand-logo" href="index.html">
                    <img src="images/logo.svg" alt="logo" />
                </a>
                <a class="navbar-brand brand-logo-mini" href="index.html">
                    <img src="images/logo-mini.svg" alt="logo" />
                </a>--%>
                <p><h4 class="text-black fw-bold">Light NMS</h4></p>
            </div>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-top">
            <%--<ul class="navbar-nav">
                <li class="nav-item font-weight-semibold d-none d-lg-block ms-0">
                    <h1 class="welcome-text">Welcome, <span class="text-black fw-bold">John Doe</span></h1>
                    <h3 class="welcome-sub-text">This is Light NMS </h3>
                </li>
            </ul>--%>

            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="dropdown-item" href="logoutProcess"><i class="dropdown-item-icon mdi mdi-power text-primary me-2"></i>Sign Out</a>
                </li>
            </ul>

        </div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial:partials/_settings-panel.html -->
        <div class="theme-setting-wrapper"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  >
            <div id="settings-trigger"><i class="ti-settings"></i></div>
            <div id="theme-settings" class="settings-panel">
                <i class="settings-close ti-close"></i>
                <p class="settings-heading">SIDEBAR SKINS</p>
                <div class="sidebar-bg-options selected" id="sidebar-light-theme"><div class="img-ss rounded-circle bg-light border me-3"></div>Light</div>
                <div class="sidebar-bg-options" id="sidebar-dark-theme"><div class="img-ss rounded-circle bg-dark border me-3"></div>Dark</div>
                <p class="settings-heading mt-2">HEADER SKINS</p>
                <div class="color-tiles mx-0 px-4">
                    <div class="tiles success"></div>
                    <div class="tiles warning"></div>
                    <div class="tiles danger"></div>
                    <div class="tiles info"></div>
                    <div class="tiles dark"></div>
                    <div class="tiles default"></div>
                </div>
            </div>
        </div>
        <div id="right-sidebar" class="settings-panel">
            <i class="settings-close ti-close"></i>
            <ul class="nav nav-tabs border-top" id="setting-panel" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="todo-tab" data-bs-toggle="tab" href="#todo-section" role="tab" aria-controls="todo-section" aria-expanded="true">TO DO LIST</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="chats-tab" data-bs-toggle="tab" href="#chats-section" role="tab" aria-controls="chats-section">CHATS</a>
                </li>
            </ul>
            <div class="tab-content" id="setting-content">
                <!-- To do section tab ends -->
                <div class="tab-pane fade" id="chats-section" role="tabpanel" aria-labelledby="chats-section">
                    <div class="d-flex align-items-center justify-content-between border-bottom">
                        <p class="settings-heading border-top-0 mb-3 pl-3 pt-0 border-bottom-0 pb-0">Friends</p>
                        <small class="settings-heading border-top-0 mb-3 pt-0 border-bottom-0 pb-0 pr-3 fw-normal">See All</small>
                    </div>

                </div>
                <!-- chat tab ends -->
            </div>

        </div>
        <!-- partial -->
        <!-- partial:partials/_sidebar.html -->
        <nav class="sidebar sidebar-offcanvas" id="sidebar">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" id="dashboard" <%--href="dashboardClick"--%>>
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
                            <li class="nav-item" id="discovery-onclick"> <a class="nav-link" <%--href="pages/ui-features/buttons.html"--%>>Discovery</a></li>
                            <li class="nav-item" id="monitor-onclick"> <a class="nav-link"> <%--href="pages/ui-features/dropdowns.html"--%>Monitor</a></li>
                        </ul>
                    </div>
                </li>
                <%--<li class="nav-item nav-category">Forms and Datas</li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#form-elements" aria-expanded="false" aria-controls="form-elements">
                        <i class="menu-icon mdi mdi-card-text-outline"></i>
                        <span class="menu-title">Form elements</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="form-elements">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"><a class="nav-link" href="pages/forms/basic_elements.html">Basic Elements</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#charts" aria-expanded="false" aria-controls="charts">
                        <i class="menu-icon mdi mdi-chart-line"></i>
                        <span class="menu-title">Charts</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="charts">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"> <a class="nav-link" href="pages/charts/chartjs.html">ChartJs</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#tables" aria-expanded="false" aria-controls="tables">
                        <i class="menu-icon mdi mdi-table"></i>
                        <span class="menu-title">Tables</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="tables">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"> <a class="nav-link" href="pages/tables/basic-table.html">Basic table</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#icons" aria-expanded="false" aria-controls="icons">
                        <i class="menu-icon mdi mdi-layers-outline"></i>
                        <span class="menu-title">Icons</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="icons">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"> <a class="nav-link" href="pages/icons/mdi.html">Mdi icons</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item nav-category">pages</li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
                        <i class="menu-icon mdi mdi-account-circle-outline"></i>
                        <span class="menu-title">User Pages</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="auth">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"> <a class="nav-link" href="pages/samples/login.html"> Login </a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item nav-category">help</li>
                <li class="nav-item">
                    <a class="nav-link" href="http://bootstrapdash.com/demo/star-admin2-free/docs/documentation.html">
                        <i class="menu-icon mdi mdi-file-document"></i>
                        <span class="menu-title">Documentation</span>
                    </a>
                </li>--%>
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


                                    <div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Dashboard</h4> <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"> <div id="upCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Up<hr><p style="font-size: 25px"></p> </div> <div id="downCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Down<hr><p style="font-size: 25px"></p> </div>  <div id="unknownCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Unknown<hr><p style="font-size: 25px"></p> </div> </div></div></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- content-wrapper ends -->
            <!-- partial:partials/_footer.html -->
            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                </div>
            </footer>
            <!-- partial -->
        </div>
        <!-- main-panel ends -->
    </div>
    <!-- page-body-wrapper ends -->
</div>

</div>

<!-- plugins:js -->
<script src="/vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->
<!-- Plugin js for this page -->
<script src="/vendors/chart.js/Chart.min.js"></script>
<script src="/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/vendors/progressbar.js/progressbar.min.js"></script>

<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="/js/off-canvas.js"></script>
<script src="/js/hoverable-collapse.js"></script>
<script src="/js/template.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->
<script src="/js/jquery.cookie.js" type="text/javascript"></script>
<%--<script src="/js/dashboard.js"></script>
<script src="/js/Chart.roundedBarCharts.js"></script>
<script src="/js/chart.js"></script>--%>
<!-- End custom js for this page-->


</body>
</html>
