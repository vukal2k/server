<?php
    require "database.php";

    $isComplete = $_GET["complete"];
    $viewModelJson = $_POST["viewModel"];
    if(isset($viewModelJson)&&isset($isComplete)){
        $viewModel = json_decode($viewModelJson);

        $query = "  SELECT n.idnhahang,n.tennhahang,n.diachi,d.ngayden,d.gioden,d.soluongnguoilon,d.soluongtreem
                    FROM datban d
                    INNER JOIN nhahang n
                    ON d.idnhahang = n.idnhahang
                    INNER JOIN taikhoan t
                    ON d.idtaikhoan=t.idtaikhoan
                    WHERE t.idtaikhoan = ".$viewModel->id;

        $filter="";
        if($viewModel->ngayDenFilterFrom!="" && $viewModel->ngayDenFilterTo!=""){
            $filter = $filter." AND d.ngayden>='".$viewModel->ngayDenFilterFrom."'
                                AND d.ngayden <='".$viewModel->ngayDenFilterTo."'";
        }
        if($viewModel->searchKey != ""){
            $filter = $filter." AND n.tennhahang LIKE '%".$viewModel->searchKey."%' 
                                OR n.sdt LIKE '%".$viewModel->searchKey."%'";
        }

        if($isComplete==0){
            $filter = $filter." AND d.tongtien = 0";
        }
        if($isComplete==1){
            $filter = $filter." AND d.tongtien > 0";
        }

        $query=$query.$filter;
        $database = new database();
        $result = $database->fetchsql($query);

        echo json_encode($result);
    }
?>

<!-- truyen ntn: http://localhost/server/getDonDatHangByKhachHang.php?complete=0
complete=1 la hoan thanh roi, 0 la chua -->
<!-- method post, pram: 
viewModel: {
	"id":"50",
	"ngayDenFilterFrom": "",
	"ngayDenFilterTo":"",
	"searchKey":""
}

class ketqua{
	.idnhahang,.tennhahang,diachi,ngayden,gioden,soluongnguoilon,soluongtreem
} -->
