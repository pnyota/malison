<!DOCTYPE html>
<html ng-app="Invoiceview">
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

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini" ng-controller="ViewCtrl">
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


      <div  class="navbar-custom-menu">
        <ul class="nav navbar-nav">
              <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="./assets/images/user.png" class="user-image" alt="User Image">
              <span class="hidden-xs"><b>User:</b><%= session.getAttribute( "username" ) %></span>
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
                  <a href="./profile.jsp" class="btn btn-default btn-flat">Edit Profile</a>
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
            <li> <button class="btn btn-danger" ng-click="deleteInvoices()"><i class="fa fa-circle-o"></i> Delete</button></li>
            <li> <button class="btn btn-primary" ng-click="showinvoicedetails(false)"><i class="fa fa-circle-o"></i>view</button></li>
          </ul>
        </li>
        </ul>
  </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->

   <div ng-show="invoicedetails">
    <section class="content-header">
      <h1>
        Invoice Number: {{invoice.invoiceNumber}}<br/>
        Company:        {{invoice.company}}<br/>
        Date:           {{invoice.date}} 
      </h1>
      <button class="btn btn-primary" ng-click="makeInvoice()">Reproduce invoice</button>
    </section>

    <!-- Main content -->
    <section class="content">
    
       <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-body">
              <table id="example1" class="table table-bordered table-striped">
                <tbody>
                      <thead>
                        <tr>
                          <th>Job id</th>
                          <th>Invoice Number</th>
                          <th>Date</th>
                          <th>Goods</th>
                          <th>Loaded From</th>
                          <th>Destination</th>
                          <th>Vehicle</th>
                          <th>Rate</th>
                          <th>Amount</th>
                        </tr>
                      </thead>
                      <tr ng-repeat="jo in jobs">
                        <td>{{jo.id}}</td>
                        <td>{{jo.invoiceNumber}}</td>
                        <td>{{jo.date}}</td>
                        <td>{{jo.product}}</td>
                        <td>{{jo.loadedFrom}}</td>
                        <td>{{jo.destination}}</td>
                        <td>{{jo.vehicleRegno}}</td>
                        <td>{{jo.rate}}</td>
                        <td>{{jo.amount}}</td>
                      </tr>
                </tbody>            
             </table>
             {{w}}
             </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
    </div>
    </section>
    </div>
    <div ng-show="invoicestable">
    <section class="content-header">
      <h1>
        Invoice
      </h1>
      <button class="btn btn-primary" ng-click="updateinvoices()">update</button>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-body">
              <table id="example1" class="table table-bordered table-striped">
                <tbody>
                  <thead>
                  <tr>
                    <th>Select</th>
                    <th>Id</th>
                    <th>Date</th>
                    <th>Invoice Number</th>
                    <th>Company</th>
                    
                    
                  </tr>
                  </thead>                
                  <tr ng-repeat = "inv in invoices">
                    <td><input type ="checkbox" ng-click="updateSelection($event, inv.id)" ng-checked="isSelected(inv.id)"></td>
                    <td>{{inv.id}}</td>
                    <td>{{inv.date}}</td>
                    <td>{{inv.invoiceNumber}}</td>
                    <td>{{inv.company}}</td>
                    
                  </tr>
              </tbody>              
             </table>
             </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 1.0.0
    </div>
    <strong>Copyright &copy; 2016 <a>Invision Itech</a>.</strong> All rights
    reserved.
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
        <script src="./assets/invoiceview.js"></script>
        <script src="./assets/dist/js/demo.js"></script>
        <script src="./assets/dist/js/app.min.js"></script>

        <script src="./assets/bootstrap/js/bootstrap.min.js"></script>



</body>
</html>
