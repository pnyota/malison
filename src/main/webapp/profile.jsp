<!DOCTYPE html>
<html ng-app="Profile">

  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Invoice Pdf Generator</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="./assets/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="./assets/plugins/datatables/dataTables.bootstrap.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="./assets/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="./assets/dist/css/skins/_all-skins.min.css">
      <style>
    .example-modal .modal {
      position: relative;
      top: auto;
      bottom: auto;
      right: auto;
      left: auto;
      display: block;
      z-index: 1;
    }

    .example-modal .modal {
      background: transparent !important;
    }
  </style>
    
  </head>

  <body class="hold-transition skin-blue sidebar-mini" ng-controller="ProfileCtrl">
    <div class="wrapper">

      <header class="main-header">
        <!-- Logo -->
        <a href="" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>I</b>PDF</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>Inv</b>PDF</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>

          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="./assets/images/user.png" class="user-image" alt="User Image">
              <span class="hidden-xs"><b>User:</b>{{user}}</span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="./assets/images/user.png" class="img-circle" alt="User Image">

                <p>
                  <%= session.getAttribute( "name" ) %>
                  <small><%= session.getAttribute( "position" ) %></small>
                </p>
              </li>
              <!--  
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
                
              </li> -->
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Edit Profile</a>
                </div>
                <div class="pull-right">
                  <a ng-click="logout()" class="btn btn-default btn-flat">Logout</a>
                </div>
              </li>
            </ul>
          </li>
            </ul>
          </div>
        </nav>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <ul class="sidebar-menu">
            <li class="header">MENU</li>
            <li><a href="./index.jsp"><i class="fa fa-circle-o"></i> Jobs</a></li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-circle-o"></i> <span>Invoice</span> <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li>
                  <a href="./invoice.jsp"><i class="fa fa-circle-o"></i> Create new invoice</a>
                </li>
                <li>
                  <a href="invoiceview.jsp"><i class="fa fa-circle-o"></i>View Invoices</a>
                </li>
              </ul>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-circle-o"></i> <span>Actions</span> <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li>
                  <button class="btn btn-danger" ng-click="deleteJobs()"><i class="fa fa-circle-o"></i> Delete</button>
                </li>
                <li>
                  <button class="btn btn-primary" ng-click="getSelectedJob()"><i class="fa fa-circle-o"></i>Edit</button>
                </li>
              </ul>
            </li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>

      <div class="content-wrapper">
        <section class="content">
             <div class="example-modal">
              <div class="modal">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">{{user.position}}</span></button>
                      <h4 class="modal-title">{{user.first + user.last}}</h4>
                    </div>
                    <div class="modal-body">
                      <p>
                        Username:{{user.username}}<br/>
                        First Name: {{user.first}}<br/>
                        Last Name: {{user.last}}<br/>
                        Position: {{user.position}}<br/>  

                      </p>
                    </div>
                    <div class="modal-footer">
                     <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Change Password</button>
                      <button type="button" class="btn btn-primary">Edit Profile</button>
                    </div>
                  </div>
                  <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
              </div>
              <!-- /.modal -->
            </div>
            <!-- /.example-modal -->
        </section>
      </div>

        <!-- /.content-wrapper -->
        <footer class="main-footer">
          <div class="pull-right hidden-xs">
            <b>Version</b> 2.3.3
          </div>
          <strong>Copyright &copy; 2016 <a href="http://almsaeedstudio.com">Invision Itech</a>.</strong> All rights reserved.
        </footer>
        </div>


        <!-- jQuery 2.2.0 -->
        <script src="./assets/plugins/jQuery/jQuery-2.2.0.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="./assets/bootstrap/js/bootstrap.min.js"></script>
        <!-- DataTables -->
        <script src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="./assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
        <!-- SlimScroll -->
        <script src="./assets/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="./assets/plugins/fastclick/fastclick.js"></script>

        <!-- page script -->
        <script src="./assets/scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
        <script src="./assets/scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
        <script src="./assets/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="./assets/angular/angular-1.5.7/angular.min.js"></script>
        <script type="text/javascript" src="./assets/angular/angular-1.5.7/angular-cookies.min.js"></script>
        <script src="./assets/profile.js"></script>
        <script src="./assets/dist/js/demo.js"></script>
        <script src="./assets/dist/js/app.min.js"></script>

        <script src="./assets/bootstrap/js/bootstrap.min.js"></script>
  </body>

</html>
