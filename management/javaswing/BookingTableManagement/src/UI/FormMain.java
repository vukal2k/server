/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import bussiness.KhuVucBUS;
import bussiness.NhaHangBUS;
import bussiness.TaiKhoanBUS;
import bussiness.ThanhPhoBUS;
import commond.ApiHelper;
import commond.ApiUrl;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import commond.ComboboxItem;
import commond.ComboboxItemRender;
import commond.FileItemToPhp;
import commond.ImageRenderer;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import model.KhuVucModel;
import model.KhuVucViewModel;
import model.NhaHangModel;
import model.NhaHangViewModel;
import model.TaiKhoanModel;
import model.ThanhPhoModel;


public class FormMain extends javax.swing.JFrame {
    // <editor-fold defaultstate="collapsed" desc="propeties"> 
    private final DefaultTableModel defaultTableThanhPho;
    private final DefaultTableModel defaultTableKhuVuc;
    private final DefaultTableModel defaultTableTaiKhoan;
    private final DefaultTableModel defaultTableNhaHang;
    private FileItemToPhp fileItemToPhp;
    private List<FileItemToPhp> listOfFileItemFromPhp= new ArrayList<>();
    // </editor-fold>
    
    public FormMain() {
        initComponents();
        setVisible(true);
        
        defaultTableThanhPho=(DefaultTableModel)jTableThanhPho.getModel();
        defaultTableKhuVuc=(DefaultTableModel)jTableKhuVuc.getModel();
        defaultTableTaiKhoan=(DefaultTableModel)jTableTaiKhoan.getModel();
        defaultTableNhaHang=(DefaultTableModel)jTableNhaHang.getModel();
        jTableNhaHang.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
        
        jComboBoxKhuVuc_ThanhPho.setRenderer( new ComboboxItemRender() );
        jComboBoxNhaHang_KhuVuc.setRenderer( new ComboboxItemRender() );
        jComboBoxNhaHang_TaiKhoan.setRenderer( new ComboboxItemRender() );
        
        LoadThanhPho();
        LoadKhuVuc();
        LoadTaiKhoan();
        LoadNhaHang();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Thanh Pho">   
    private void LoadThanhPho(){
        ArrayList listThanhPho = ThanhPhoBUS.getAll();
        ThanhPhoModel thanhPho;
        defaultTableThanhPho.setRowCount(0);
        
        for (int i = 0; i < listThanhPho.size(); i++) {
            thanhPho= (ThanhPhoModel)listThanhPho.get(i);
            
            defaultTableThanhPho.insertRow(i, new Object[]{
                thanhPho.getIdthanhpho(),thanhPho.getTenthanhpho()
            });
        }
        LoadComboboxKhuVuc_ThanhPho();
    }
    
    private void TimThanhPho(){
        ArrayList listThanhPho = ThanhPhoBUS.timKiem(jTextFieldTimThanhPho.getText());
        ThanhPhoModel thanhPho;
        defaultTableThanhPho.setRowCount(0);
        
        for (int i = 0; i < listThanhPho.size(); i++) {
            thanhPho= (ThanhPhoModel)listThanhPho.get(i);
            
            defaultTableThanhPho.insertRow(i, new Object[]{
                thanhPho.getIdthanhpho(),thanhPho.getTenthanhpho()
            });
        }
    }
    
    private void ChonThanhPho(){
        jTextFieldThanhPhoTen.setText(defaultTableThanhPho.getValueAt(jTableThanhPho.getSelectedRow(), 1).toString());
    }
    
    private void ThemThanhPho(){
        ThanhPhoModel thanhPho = new ThanhPhoModel();
        thanhPho.setTenthanhpho(jTextFieldThanhPhoTen.getText());
        
        JOptionPane.showMessageDialog(null, ThanhPhoBUS.Them(thanhPho));
        LoadThanhPho();
    }
    
    private void SuaThanhPho(){
        try {
            ThanhPhoModel thanhPho = new ThanhPhoModel();
            thanhPho.setTenthanhpho(jTextFieldThanhPhoTen.getText());
            thanhPho.setIdthanhpho((int) defaultTableThanhPho.getValueAt(jTableThanhPho.getSelectedRow(), 0));

            JOptionPane.showMessageDialog(null, ThanhPhoBUS.Sua(thanhPho));
            LoadThanhPho();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn thành phố cần sửa");
        }
    }
    
    private void XoaThanhPho(){
        try {
            int idThanhPho = (int) defaultTableThanhPho.getValueAt(jTableThanhPho.getSelectedRow(), 0);
        
            JOptionPane.showMessageDialog(null, ThanhPhoBUS.Xoa(idThanhPho));
            LoadThanhPho();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn thành phố cần xóa");
        }
    }
    //  </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Khu Vuc">
    private void LoadKhuVuc(){
        ArrayList listKhuVuc = KhuVucBUS.getAll();
        KhuVucViewModel khuVuc;
        defaultTableKhuVuc.setRowCount(0);
        
        for (int i = 0; i < listKhuVuc.size(); i++) {
            khuVuc= (KhuVucViewModel)listKhuVuc.get(i);
            
            defaultTableKhuVuc.insertRow(i, new Object[]{
                khuVuc.getIdkhuvuc(),khuVuc.getTenkhuvuc(),khuVuc.getTenthanhpho()
            });
        }
        
        LoadComboboxNhaHang_KhuVuc();
    }
    
    private void TimKhuVuc(){
        ArrayList listKhuVuc = KhuVucBUS.timKiem(jTextFieldTimKhuVuc.getText());
        KhuVucViewModel khuVuc;
        defaultTableKhuVuc.setRowCount(0);
        
        for (int i = 0; i < listKhuVuc.size(); i++) {
            khuVuc= (KhuVucViewModel)listKhuVuc.get(i);
            
            defaultTableKhuVuc.insertRow(i, new Object[]{
                khuVuc.getIdkhuvuc(),khuVuc.getTenkhuvuc(),khuVuc.getTenthanhpho()
            });
        }
    }
    
    private void ChonKhuVuc(){
        int selectedRowIndex = jTableKhuVuc.getSelectedRow();
        jTextFieldKhuVucTen.setText(defaultTableKhuVuc.getValueAt(selectedRowIndex, 1).toString());
        
        String selectedThanhPho = defaultTableKhuVuc.getValueAt(selectedRowIndex, 2).toString();
        ComboboxItem item;
        for (int i = 0; i < jComboBoxKhuVuc_ThanhPho.getItemCount(); i++)
        {
            item = (ComboboxItem)jComboBoxKhuVuc_ThanhPho.getItemAt(i);
            if (item.getValue().equalsIgnoreCase(selectedThanhPho))
            {
                jComboBoxKhuVuc_ThanhPho.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void ThemKhuVuc(){
        KhuVucModel khuVuc = new KhuVucModel();
        khuVuc.setTenkhuvuc(jTextFieldKhuVucTen.getText());
        
        ComboboxItem selectedThanhPho = (ComboboxItem)jComboBoxKhuVuc_ThanhPho.getSelectedItem();
        khuVuc.setIdthanhpho(selectedThanhPho.getKey());
        
        JOptionPane.showMessageDialog(null, KhuVucBUS.Them(khuVuc));
        LoadKhuVuc();
    }
    
    private void SuaKhuVuc(){
        try {
            KhuVucModel khuVuc = new KhuVucModel();
            khuVuc.setTenkhuvuc(jTextFieldKhuVucTen.getText());
            
            int selectedRowIndex = jTableKhuVuc.getSelectedRow();
            khuVuc.setIdkhuvuc(Integer.parseInt(jTableKhuVuc.getValueAt(selectedRowIndex, 0).toString()));

            ComboboxItem selectedThanhPho = (ComboboxItem)jComboBoxKhuVuc_ThanhPho.getSelectedItem();
            khuVuc.setIdthanhpho(selectedThanhPho.getKey());

            JOptionPane.showMessageDialog(null, KhuVucBUS.Sua(khuVuc));
            LoadKhuVuc();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn khu vực cần sửa");
        }
    }
    
    private void XoaKhuVuc(){
        try {
            int idKhuVuc = (int) defaultTableKhuVuc.getValueAt(jTableKhuVuc.getSelectedRow(), 0);
        
            JOptionPane.showMessageDialog(null, KhuVucBUS.Xoa(idKhuVuc));
            LoadKhuVuc();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn khu vực cần xóa");
        }
    }
    
    private void LoadComboboxKhuVuc_ThanhPho(){
      ArrayList listThanhPhoItem = ThanhPhoBUS.getAll();
      ThanhPhoModel thanhPho;
      
      jComboBoxKhuVuc_ThanhPho.removeAllItems();
      ComboboxItem fistComboboxItem = new ComboboxItem(0, "Chọn thành phố");
      jComboBoxKhuVuc_ThanhPho.addItem(fistComboboxItem);
        for (int i = 0; i < listThanhPhoItem.size(); i++) {
            thanhPho = (ThanhPhoModel)listThanhPhoItem.get(i);
            
            jComboBoxKhuVuc_ThanhPho.addItem(new ComboboxItem(thanhPho.getIdthanhpho(), thanhPho.getTenthanhpho()));
        }
    }
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Tai Khoan">   
    private void LoadTaiKhoan(){
        ArrayList listTaiKhoan = TaiKhoanBUS.getAll();
        TaiKhoanModel taiKhoan;
        defaultTableTaiKhoan.setRowCount(0);
        
        for (int i = 0; i < listTaiKhoan.size(); i++) {
            taiKhoan= (TaiKhoanModel)listTaiKhoan.get(i);
            
            defaultTableTaiKhoan.insertRow(i, new Object[]{
                taiKhoan.getIdtaikhoan(),taiKhoan.getUsername(),taiKhoan.getPassword(),
                taiKhoan.getTenkhachhang(),taiKhoan.getSdt(),taiKhoan.getEmail()
            });
        }
        LoadComboboxNhaHang_TaiKhoan();
    }
    
    private void TimTaiKhoan(){
        ArrayList listTaiKhoan = TaiKhoanBUS.timKiem(jTextFieldTimTaiKhoan.getText());
        TaiKhoanModel taiKhoan;
        defaultTableTaiKhoan.setRowCount(0);
        
        for (int i = 0; i < listTaiKhoan.size(); i++) {
            taiKhoan= (TaiKhoanModel)listTaiKhoan.get(i);
            
            defaultTableTaiKhoan.insertRow(i, new Object[]{
                taiKhoan.getIdtaikhoan(),taiKhoan.getUsername(),taiKhoan.getPassword(),
                taiKhoan.getTenkhachhang(),taiKhoan.getSdt(),taiKhoan.getEmail()
            });
        }
    }
    
    private void ChonTaiKhoan(){
        jTextFieldTaiKhoanUsername.setText(defaultTableTaiKhoan.getValueAt(jTableTaiKhoan.getSelectedRow(), 1).toString());
        jTextFieldTaiKhoanPassword.setText(defaultTableTaiKhoan.getValueAt(jTableTaiKhoan.getSelectedRow(), 2).toString());
        jTextFieldTaiKhoanTen.setText(defaultTableTaiKhoan.getValueAt(jTableTaiKhoan.getSelectedRow(), 3).toString());
        jTextFieldTaiKhoanSdt.setText(defaultTableTaiKhoan.getValueAt(jTableTaiKhoan.getSelectedRow(), 4).toString());
        jTextFieldTaiKhoanEmail.setText(defaultTableTaiKhoan.getValueAt(jTableTaiKhoan.getSelectedRow(), 5).toString());
    }
    
    private void ThemTaiKhoan(){
        TaiKhoanModel taiKhoan = new TaiKhoanModel();
        taiKhoan.setUsername(jTextFieldTaiKhoanUsername.getText());
        taiKhoan.setPassword(jTextFieldTaiKhoanPassword.getText());
        taiKhoan.setTenkhachhang(jTextFieldTaiKhoanTen.getText());
        taiKhoan.setSdt(jTextFieldTaiKhoanSdt.getText());
        taiKhoan.setEmail(jTextFieldTaiKhoanEmail.getText());
        
        JOptionPane.showMessageDialog(null, TaiKhoanBUS.Them(taiKhoan));
        LoadTaiKhoan();
    }
    
    private void SuaTaiKhoan(){
        try {
            TaiKhoanModel taiKhoan = new TaiKhoanModel();
            taiKhoan.setUsername(jTextFieldTaiKhoanUsername.getText());
            taiKhoan.setPassword(jTextFieldTaiKhoanPassword.getText());
            taiKhoan.setTenkhachhang(jTextFieldTaiKhoanTen.getText());
            taiKhoan.setSdt(jTextFieldTaiKhoanSdt.getText());
            taiKhoan.setEmail(jTextFieldTaiKhoanEmail.getText());
            taiKhoan.setIdtaikhoan((int) defaultTableTaiKhoan.getValueAt(jTableTaiKhoan.getSelectedRow(), 0));

            JOptionPane.showMessageDialog(null, TaiKhoanBUS.Sua(taiKhoan));
            LoadTaiKhoan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn tài khoản cần sửa");
        }
    }
    
    private void XoaTaiKhoan(){
        try {
            int idTaiKhoan = (int) defaultTableTaiKhoan.getValueAt(jTableTaiKhoan.getSelectedRow(), 0);
        
            JOptionPane.showMessageDialog(null, TaiKhoanBUS.Xoa(idTaiKhoan));
            LoadTaiKhoan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn tài khoản cần xóa");
        }
    }
    //  </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Nha Hang">
    private void LoadNhaHang(){
            ArrayList listNhaHang = NhaHangBUS.getAll();
            NhaHangViewModel nhaHang;
            defaultTableNhaHang.setRowCount(0);
            listOfFileItemFromPhp.clear();
            
            URL url;
            BufferedImage img;
            @SuppressWarnings("UnusedAssignment")
            ImageIcon objectHinhAnh=null;
            
            for (int i = 0; i < listNhaHang.size(); i++) {
                try {
                    nhaHang= (NhaHangViewModel)listNhaHang.get(i);
                    
                    if(ApiHelper.checkImageExsists(ApiUrl.HostImage+nhaHang.getHinhanh())==false){
                        img = ImageIO.read(getClass().getClassLoader().getResource("resources/no_img.png"));
                        objectHinhAnh =new ImageIcon(img.getScaledInstance(100,
                                50,
                                Image.SCALE_DEFAULT));
                        listOfFileItemFromPhp.add(new FileItemToPhp("", ""));
                    }else{
                        url = new URL(ApiUrl.HostImage+nhaHang.getHinhanh());
                        //url=new URL("http://sleeping.somee.com/Asset/img/roi-vi-vua-chuong-1.jpg");
                        //url = new URL(ApiUrl.HostImage+"/img/20190609155635_1.jpg");
                        img = ImageIO.read(url);
                        objectHinhAnh =new ImageIcon(img.getScaledInstance(100,50,
                                Image.SCALE_DEFAULT));
                        listOfFileItemFromPhp.add(new FileItemToPhp("", ApiUrl.HostImage+nhaHang.getHinhanh()));
                    }
                    defaultTableNhaHang.insertRow(i, new Object[]{
                        nhaHang.getIdnhahang(),objectHinhAnh ,
                        nhaHang.getTennhahang(),nhaHang.getDiachi(),
                        nhaHang.getTenkhuvuc(),nhaHang.getUsername(),
                        nhaHang.getKhoangtien(),nhaHang.getLoaihinh(),
                        nhaHang.getSdt(),nhaHang.getUudai(),
                        nhaHang.getGiodonkhach(), nhaHang.getMota()
                    });
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    private void TimNhaHang(){
        ArrayList listNhaHang = NhaHangBUS.timKiem(jTextFieldTimNhaHang.getText());
        NhaHangViewModel nhaHang;
            defaultTableNhaHang.setRowCount(0);
            listOfFileItemFromPhp.clear();
            
            URL url;
            BufferedImage img;
            @SuppressWarnings("UnusedAssignment")
            ImageIcon objectHinhAnh=null;
            
            for (int i = 0; i < listNhaHang.size(); i++) {
                try {
                    nhaHang= (NhaHangViewModel)listNhaHang.get(i);
                    
                    if(ApiHelper.checkImageExsists(ApiUrl.HostImage+nhaHang.getHinhanh())==false){
                        img = ImageIO.read(getClass().getClassLoader().getResource("resources/no_img.png"));
                        objectHinhAnh =new ImageIcon(img.getScaledInstance(100,
                                50,
                                Image.SCALE_DEFAULT));
                        listOfFileItemFromPhp.add(new FileItemToPhp("", ""));
                    }else{
                        url = new URL(ApiUrl.HostImage+nhaHang.getHinhanh());
                        //url=new URL("http://sleeping.somee.com/Asset/img/roi-vi-vua-chuong-1.jpg");
                        //url = new URL(ApiUrl.HostImage+"/img/20190609155635_1.jpg");
                        img = ImageIO.read(url);
                        objectHinhAnh =new ImageIcon(img.getScaledInstance(100,50,
                                Image.SCALE_DEFAULT));
                        listOfFileItemFromPhp.add(new FileItemToPhp("", ApiUrl.HostImage+nhaHang.getHinhanh()));
                    }
                    defaultTableNhaHang.insertRow(i, new Object[]{
                        nhaHang.getIdnhahang(),objectHinhAnh ,
                        nhaHang.getTennhahang(),nhaHang.getDiachi(),
                        nhaHang.getTenkhuvuc(),nhaHang.getUsername(),
                        nhaHang.getKhoangtien(),nhaHang.getLoaihinh(),
                        nhaHang.getSdt(),nhaHang.getUudai(),
                        nhaHang.getGiodonkhach(), nhaHang.getMota()
                    });
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    private void ChonNhaHang(){
        int selectedRowIndex=jTableNhaHang.getSelectedRow();
        
        jTextFieldNhaHangTen.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 2).toString());
        
        //set hinh anh
        fileItemToPhp=null; //set curent file to php í null if update do not change hinh anh
        
        URL url;
        BufferedImage img;
        @SuppressWarnings("UnusedAssignment")
        ImageIcon objectHinhAnh=null;
        FileItemToPhp fileFromPhp = listOfFileItemFromPhp.get(selectedRowIndex);
        try {
            if(fileFromPhp.getFilePath().equals("")==true){
                img = ImageIO.read(getClass().getClassLoader().getResource("resources/no_img.png"));
                objectHinhAnh =new ImageIcon(img.getScaledInstance(jLabelNhaHangHinhAnh.getWidth(), 
                                                                   jLabelNhaHangHinhAnh.getHeight(), 
                                                                    Image.SCALE_DEFAULT));
            }else{
                url = new URL(fileFromPhp.getFilePath());
                img = ImageIO.read(url);
                objectHinhAnh =new ImageIcon(img.getScaledInstance(jLabelNhaHangHinhAnh.getWidth(), 
                                                                    jLabelNhaHangHinhAnh.getHeight(), 
                                                                    Image.SCALE_DEFAULT));
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        jLabelNhaHangHinhAnh.setIcon(objectHinhAnh);
        
        //set dia ch
        jTextFieldNhaHangDiaChi.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 3).toString());
        String selectedKhuVuc = defaultTableNhaHang.getValueAt(selectedRowIndex, 4).toString();
        ComboboxItem item;
        for (int i = 0; i < jComboBoxNhaHang_KhuVuc.getItemCount(); i++)
        {
            item = (ComboboxItem)jComboBoxNhaHang_KhuVuc.getItemAt(i);
            if (item.getValue().equalsIgnoreCase(selectedKhuVuc))
            {
                jComboBoxNhaHang_KhuVuc.setSelectedIndex(i);
                break;
            }
        }
        String selectedTaiKhoan = defaultTableNhaHang.getValueAt(selectedRowIndex, 5).toString();
        for (int i = 0; i < jComboBoxNhaHang_TaiKhoan.getItemCount(); i++)
        {
            item = (ComboboxItem)jComboBoxNhaHang_TaiKhoan.getItemAt(i);
            if (item.getValue().equalsIgnoreCase(selectedTaiKhoan))
            {
                jComboBoxNhaHang_TaiKhoan.setSelectedIndex(i);
                break;
            }
        }
        jTextFieldNhaHangKhoangTien.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 6).toString());
        jTextFieldNhaHangLoaiHinh.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 7).toString());
        jTextFieldNhaHangSdt.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 8).toString());
        jTextFieldNhaHangUuDai.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 9).toString());
        jTextFieldNhaHangGioDonKhach.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 10).toString());
        jTextAreaNhaHangMoTa.setText(defaultTableNhaHang.getValueAt(selectedRowIndex, 11).toString());
    }
    
    private void ShowNhaHangDetail(){
        NhaHangModel nhaHang = new NhaHangModel();
        int currentRow = jTableNhaHang.getSelectedRow();
        
        nhaHang.setIdnhahang((int) defaultTableNhaHang.getValueAt(currentRow, 0));
        nhaHang.setTennhahang(defaultTableNhaHang.getValueAt(currentRow, 2).toString());
        
        FormNhaHangDetail detail = new FormNhaHangDetail(nhaHang,this);
        detail.setVisible(true);
        this.setVisible(false);
    }
    
    private void ThemNhaHang(){
        NhaHangModel nhaHang = new NhaHangModel();
        nhaHang.setTennhahang(jTextFieldNhaHangTen.getText());
        nhaHang.setDiachi(jTextFieldNhaHangDiaChi.getText());
        nhaHang.setGiodonkhach(jTextFieldNhaHangGioDonKhach.getText());
        
        ComboboxItem itemSelected=(ComboboxItem)jComboBoxNhaHang_KhuVuc.getSelectedItem();
        nhaHang.setIdkhuvuc(itemSelected.getKey());
        
        itemSelected=(ComboboxItem)jComboBoxNhaHang_TaiKhoan.getSelectedItem();
        nhaHang.setIdtaikhoan(itemSelected.getKey());
        
        nhaHang.setKhoangtien(jTextFieldNhaHangKhoangTien.getText());
        nhaHang.setLoaihinh(jTextFieldNhaHangLoaiHinh.getText());
        nhaHang.setMota(jTextAreaNhaHangMoTa.getText());
        nhaHang.setSdt(jTextFieldNhaHangSdt.getText());
        nhaHang.setUudai(jTextFieldNhaHangUuDai.getText());
        
        JOptionPane.showMessageDialog(null, NhaHangBUS.Them(nhaHang,fileItemToPhp));
        LoadNhaHang();
    }
    
    private void SuaNhaHang(){
        try {
            NhaHangModel nhaHang = new NhaHangModel();
            nhaHang.setIdnhahang((int) defaultTableNhaHang.getValueAt(jTableNhaHang.getSelectedRow(), 0));
            nhaHang.setTennhahang(jTextFieldNhaHangTen.getText());
            nhaHang.setDiachi(jTextFieldNhaHangDiaChi.getText());
            nhaHang.setGiodonkhach(jTextFieldNhaHangGioDonKhach.getText());

            ComboboxItem itemSelected=(ComboboxItem)jComboBoxNhaHang_KhuVuc.getSelectedItem();
            nhaHang.setIdkhuvuc(itemSelected.getKey());

            itemSelected=(ComboboxItem)jComboBoxNhaHang_TaiKhoan.getSelectedItem();
            nhaHang.setIdtaikhoan(itemSelected.getKey());

            nhaHang.setKhoangtien(jTextFieldNhaHangKhoangTien.getText());
            nhaHang.setLoaihinh(jTextFieldNhaHangLoaiHinh.getText());
            nhaHang.setMota(jTextAreaNhaHangMoTa.getText());
            nhaHang.setSdt(jTextFieldNhaHangSdt.getText());
            nhaHang.setUudai(jTextFieldNhaHangUuDai.getText());
            
            //set hinh anh nhu cu
            //xoa localhost trong list hinh anh
            String oldHinhAnhUrlInDb=listOfFileItemFromPhp.get(jTableNhaHang.getSelectedRow()).getFilePath().replaceAll(ApiUrl.HostImage, "");
            nhaHang.setHinhanh(oldHinhAnhUrlInDb); 

            JOptionPane.showMessageDialog(null, NhaHangBUS.Sua(nhaHang,fileItemToPhp));
            LoadNhaHang();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Chọn nhà hàng cần sửa");
        } catch (IOException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void XoaNhaHang(){
        try {
            int idNhaHang = (int) defaultTableNhaHang.getValueAt(jTableNhaHang.getSelectedRow(), 0);
        
            JOptionPane.showMessageDialog(null, NhaHangBUS.Xoa(idNhaHang));
            LoadNhaHang();
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Chọn nhà hàng cần xóa");
        }
    }
    
    private void LoadComboboxNhaHang_KhuVuc(){
        ArrayList listKhuVucItem = KhuVucBUS.getAll();
        KhuVucViewModel khuVuc;

        jComboBoxNhaHang_KhuVuc.removeAllItems();
        ComboboxItem fistComboboxItem = new ComboboxItem(0, "Chọn khu vực");
        jComboBoxNhaHang_KhuVuc.addItem(fistComboboxItem);
        for (int i = 0; i < listKhuVucItem.size(); i++) {
            khuVuc = (KhuVucViewModel)listKhuVucItem.get(i);
            
            jComboBoxNhaHang_KhuVuc.addItem(new ComboboxItem(khuVuc.getIdkhuvuc(), khuVuc.getTenkhuvuc()));
        }
    }
    
    private void LoadComboboxNhaHang_TaiKhoan(){
        ArrayList listTaiKhoanItem = TaiKhoanBUS.getAll();
        TaiKhoanModel taiKhoan;

        jComboBoxNhaHang_TaiKhoan.removeAllItems();
        ComboboxItem fistComboboxItem = new ComboboxItem(0, "Chọn chủ sở hữu");
        jComboBoxNhaHang_TaiKhoan.addItem(fistComboboxItem);
        for (int i = 0; i < listTaiKhoanItem.size(); i++) {
            taiKhoan = (TaiKhoanModel)listTaiKhoanItem.get(i);
            
            jComboBoxNhaHang_TaiKhoan.addItem(new ComboboxItem(taiKhoan.getIdtaikhoan(), taiKhoan.getUsername()));
        }
    }
    
    private void UploadHinhAnhNhaHang(){
        try {
            JFileChooser fileChoose = new JFileChooser();
            fileChoose.showOpenDialog(null);
            
            File nhaHangHinhAnh = fileChoose.getSelectedFile();
            Image img = ImageIO.read(nhaHangHinhAnh);
            Image resizedImage = img.getScaledInstance(jLabelNhaHangHinhAnh.getWidth(), jLabelNhaHangHinhAnh.getHeight(), 0);
            
            jLabelNhaHangHinhAnh.setIcon(new ImageIcon(resizedImage));
            
            fileItemToPhp= new FileItemToPhp(nhaHangHinhAnh.getName(), nhaHangHinhAnh.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // </editor-fold>
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabMain = new javax.swing.JTabbedPane();
        jPanelThanhPho = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableThanhPho = new javax.swing.JTable();
        jTextFieldTimThanhPho = new javax.swing.JTextField();
        jToggleButtonTimThanhPho = new javax.swing.JToggleButton();
        jTextFieldThanhPhoTen = new javax.swing.JTextField();
        jButtonThemThanhPho = new javax.swing.JButton();
        jButtonSuaThanhPho = new javax.swing.JButton();
        jButtonXoaThanhPho = new javax.swing.JButton();
        jButtonResetThanhPho = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanelKhuVuc = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableKhuVuc = new javax.swing.JTable();
        jTextFieldTimKhuVuc = new javax.swing.JTextField();
        jToggleButtonTimKhuVuc = new javax.swing.JToggleButton();
        jButtonResetKhuVuc = new javax.swing.JButton();
        jTextFieldKhuVucTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButtonThemKhuVuc = new javax.swing.JButton();
        jButtonSuaKhuVuc = new javax.swing.JButton();
        jButtonXoaKhuVuc = new javax.swing.JButton();
        jComboBoxKhuVuc_ThanhPho = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanelNhaHang = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableNhaHang = new javax.swing.JTable();
        jTextFieldTimNhaHang = new javax.swing.JTextField();
        jButtonTimNhaHang = new javax.swing.JButton();
        jButtonResetNhaHang = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTextFieldNhaHangTen = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxNhaHang_TaiKhoan = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxNhaHang_KhuVuc = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldNhaHangDiaChi = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldNhaHangKhoangTien = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldNhaHangSdt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldNhaHangLoaiHinh = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaNhaHangMoTa = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldNhaHangGioDonKhach = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldNhaHangUuDai = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jButtonUploadNhaHangHinhAnh = new javax.swing.JButton();
        jLabelNhaHangHinhAnh = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButtonThemNhaHang = new javax.swing.JButton();
        jButtonSuaNhaHang = new javax.swing.JButton();
        jButtonXoaNhaHang = new javax.swing.JButton();
        jPanelTaiKhoan = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableTaiKhoan = new javax.swing.JTable();
        jTextFieldTimTaiKhoan = new javax.swing.JTextField();
        jToggleButtonTimTaiKhoan = new javax.swing.JToggleButton();
        jButtonResetTaiKhoan = new javax.swing.JButton();
        jTextFieldTaiKhoanUsername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButtonThemTaiKhoan = new javax.swing.JButton();
        jButtonSuaTaiKhoan = new javax.swing.JButton();
        jButtonXoaTaiKhoan = new javax.swing.JButton();
        jTextFieldTaiKhoanPassword = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldTaiKhoanTen = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldTaiKhoanSdt = new javax.swing.JTextField();
        jTextFieldTaiKhoanEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Trang Chủ");
        setBackground(new java.awt.Color(255, 51, 51));
        setForeground(new java.awt.Color(255, 51, 51));
        setResizable(false);

        jTableThanhPho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tên Thành Phố"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableThanhPho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChonThanhPho(evt);
            }
        });
        jScrollPane1.setViewportView(jTableThanhPho);

        jToggleButtonTimThanhPho.setText("Tìm Kiếm");
        jToggleButtonTimThanhPho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTimThanhPhoActionPerformed(evt);
            }
        });

        jButtonThemThanhPho.setText("Thêm");
        jButtonThemThanhPho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemThanhPhoActionPerformed(evt);
            }
        });

        jButtonSuaThanhPho.setText("Sửa");
        jButtonSuaThanhPho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaThanhPhoActionPerformed(evt);
            }
        });

        jButtonXoaThanhPho.setText("Xóa");
        jButtonXoaThanhPho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaThanhPhoActionPerformed(evt);
            }
        });

        jButtonResetThanhPho.setText("Refresh");
        jButtonResetThanhPho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetThanhPhoActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên Thành Phố");

        javax.swing.GroupLayout jPanelThanhPhoLayout = new javax.swing.GroupLayout(jPanelThanhPho);
        jPanelThanhPho.setLayout(jPanelThanhPhoLayout);
        jPanelThanhPhoLayout.setHorizontalGroup(
            jPanelThanhPhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelThanhPhoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addGroup(jPanelThanhPhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThanhPhoLayout.createSequentialGroup()
                        .addComponent(jTextFieldTimThanhPho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButtonTimThanhPho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonResetThanhPho))
                    .addGroup(jPanelThanhPhoLayout.createSequentialGroup()
                        .addComponent(jButtonThemThanhPho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSuaThanhPho)
                        .addGap(69, 69, 69)
                        .addComponent(jButtonXoaThanhPho))
                    .addGroup(jPanelThanhPhoLayout.createSequentialGroup()
                        .addGroup(jPanelThanhPhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldThanhPhoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelThanhPhoLayout.setVerticalGroup(
            jPanelThanhPhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
            .addGroup(jPanelThanhPhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelThanhPhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimThanhPho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonTimThanhPho)
                    .addComponent(jButtonResetThanhPho))
                .addGap(94, 94, 94)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldThanhPhoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelThanhPhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonThemThanhPho)
                    .addComponent(jButtonSuaThanhPho)
                    .addComponent(jButtonXoaThanhPho))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabMain.addTab("Thành Phố", jPanelThanhPho);

        jTableKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tên Khu Vực", "Tên Thành Phố"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableKhuVuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableKhuVucChonThanhPho(evt);
            }
        });
        jScrollPane2.setViewportView(jTableKhuVuc);

        jToggleButtonTimKhuVuc.setText("Tìm Kiếm");
        jToggleButtonTimKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTimKhuVucActionPerformed(evt);
            }
        });

        jButtonResetKhuVuc.setText("Refresh");
        jButtonResetKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetKhuVucActionPerformed(evt);
            }
        });

        jLabel3.setText("Tên Khu Vực");

        jButtonThemKhuVuc.setText("Thêm");
        jButtonThemKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemKhuVucActionPerformed(evt);
            }
        });

        jButtonSuaKhuVuc.setText("Sửa");
        jButtonSuaKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaKhuVucActionPerformed(evt);
            }
        });

        jButtonXoaKhuVuc.setText("Xóa");
        jButtonXoaKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaKhuVucActionPerformed(evt);
            }
        });

        jLabel4.setText("Thành Phố");

        javax.swing.GroupLayout jPanelKhuVucLayout = new javax.swing.GroupLayout(jPanelKhuVuc);
        jPanelKhuVuc.setLayout(jPanelKhuVucLayout);
        jPanelKhuVucLayout.setHorizontalGroup(
            jPanelKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelKhuVucLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addGroup(jPanelKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKhuVucLayout.createSequentialGroup()
                        .addComponent(jTextFieldTimKhuVuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButtonTimKhuVuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonResetKhuVuc))
                    .addGroup(jPanelKhuVucLayout.createSequentialGroup()
                        .addComponent(jButtonThemKhuVuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSuaKhuVuc)
                        .addGap(69, 69, 69)
                        .addComponent(jButtonXoaKhuVuc))
                    .addGroup(jPanelKhuVucLayout.createSequentialGroup()
                        .addGroup(jPanelKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldKhuVucTen, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jComboBoxKhuVuc_ThanhPho, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelKhuVucLayout.setVerticalGroup(
            jPanelKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
            .addGroup(jPanelKhuVucLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonTimKhuVuc)
                    .addComponent(jButtonResetKhuVuc))
                .addGap(104, 104, 104)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldKhuVucTen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxKhuVuc_ThanhPho, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonThemKhuVuc)
                    .addComponent(jButtonSuaKhuVuc)
                    .addComponent(jButtonXoaKhuVuc))
                .addGap(83, 83, 83))
        );

        tabMain.addTab("Khu Vực", jPanelKhuVuc);

        jTableNhaHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Hình Ảnh", "Tên Nhà Hang", "Địa Chỉ", "Khu Vực", "Tài Khoản Sở Hữu", "Khoảng Tiền", "Loại Hình", "Sđt", "Ưu Đãi", "Giờ Đón Khách", "Mô Tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableNhaHang.setRowHeight(50);
        jTableNhaHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableNhaHangChonThanhPho(evt);
            }
        });
        jScrollPane4.setViewportView(jTableNhaHang);

        jButtonTimNhaHang.setText("Tìm Kiếm");
        jButtonTimNhaHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimNhaHangActionPerformed(evt);
            }
        });

        jButtonResetNhaHang.setText("Refresh");
        jButtonResetNhaHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetNhaHangActionPerformed(evt);
            }
        });

        jLabel10.setText("Tên Nhà Hàng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldNhaHangTen)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10)
                .addComponent(jTextFieldNhaHangTen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel13.setText("Tài Khoản Sở Hữu");

        jLabel12.setText("Khu Vực");

        jLabel11.setText("Địa Chỉ");

        jLabel16.setText("Sđt");

        jLabel14.setText("Khoảng Tiền");

        jLabel15.setText("Loại Hình");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldNhaHangDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxNhaHang_KhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxNhaHang_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldNhaHangLoaiHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldNhaHangKhoangTien, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldNhaHangSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldNhaHangDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxNhaHang_KhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxNhaHang_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldNhaHangKhoangTien, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldNhaHangLoaiHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldNhaHangSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jTextAreaNhaHangMoTa.setColumns(20);
        jTextAreaNhaHangMoTa.setRows(5);
        jScrollPane5.setViewportView(jTextAreaNhaHangMoTa);

        jLabel19.setText("Mô Tả");

        jLabel17.setText("Ưu Đãi");

        jLabel18.setText("Giờ Đón Khách");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldNhaHangUuDai, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(11, 11, 11)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNhaHangGioDonKhach, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldNhaHangUuDai, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldNhaHangGioDonKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );

        jLabel21.setText("Hình Ảnh");

        jButtonUploadNhaHangHinhAnh.setText("Upload");
        jButtonUploadNhaHangHinhAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUploadNhaHangHinhAnhActionPerformed(evt);
            }
        });

        jLabelNhaHangHinhAnh.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelNhaHangHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonUploadNhaHangHinhAnh)
                            .addGap(0, 213, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(jButtonUploadNhaHangHinhAnh))
                    .addGap(4, 4, 4)
                    .addComponent(jLabelNhaHangHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jButtonThemNhaHang.setText("Thêm");
        jButtonThemNhaHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemNhaHangActionPerformed(evt);
            }
        });

        jButtonSuaNhaHang.setText("Sửa");
        jButtonSuaNhaHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaNhaHangActionPerformed(evt);
            }
        });

        jButtonXoaNhaHang.setText("Xóa");
        jButtonXoaNhaHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaNhaHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSuaNhaHang)
                    .addComponent(jButtonXoaNhaHang))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButtonThemNhaHang)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jButtonSuaNhaHang)
                .addGap(18, 18, 18)
                .addComponent(jButtonXoaNhaHang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jButtonThemNhaHang)
                    .addContainerGap(149, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanelNhaHangLayout = new javax.swing.GroupLayout(jPanelNhaHang);
        jPanelNhaHang.setLayout(jPanelNhaHangLayout);
        jPanelNhaHangLayout.setHorizontalGroup(
            jPanelNhaHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNhaHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldTimNhaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonTimNhaHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonResetNhaHang)
                .addContainerGap())
            .addGroup(jPanelNhaHangLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanelNhaHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNhaHangLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        jPanelNhaHangLayout.setVerticalGroup(
            jPanelNhaHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNhaHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNhaHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimNhaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonTimNhaHang)
                    .addComponent(jButtonResetNhaHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNhaHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tabMain.addTab("Nhà Hàng", jPanelNhaHang);

        jTableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Username", "Password", "Tên Khách Hàng", "Sđt", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableTaiKhoanChonThanhPho(evt);
            }
        });
        jScrollPane3.setViewportView(jTableTaiKhoan);

        jToggleButtonTimTaiKhoan.setText("Tìm Kiếm");
        jToggleButtonTimTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTimTaiKhoanActionPerformed(evt);
            }
        });

        jButtonResetTaiKhoan.setText("Refresh");
        jButtonResetTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetTaiKhoanActionPerformed(evt);
            }
        });

        jLabel5.setText("Username");

        jButtonThemTaiKhoan.setText("Thêm");
        jButtonThemTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemTaiKhoanActionPerformed(evt);
            }
        });

        jButtonSuaTaiKhoan.setText("Sửa");
        jButtonSuaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaTaiKhoanActionPerformed(evt);
            }
        });

        jButtonXoaTaiKhoan.setText("Xóa");
        jButtonXoaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaTaiKhoanActionPerformed(evt);
            }
        });

        jLabel6.setText("Password");

        jLabel7.setText("Tên Khách Hàng");

        jLabel8.setText("Số điện thoại");

        jLabel9.setText("Email");

        javax.swing.GroupLayout jPanelTaiKhoanLayout = new javax.swing.GroupLayout(jPanelTaiKhoan);
        jPanelTaiKhoan.setLayout(jPanelTaiKhoanLayout);
        jPanelTaiKhoanLayout.setHorizontalGroup(
            jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTaiKhoanLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addGroup(jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTaiKhoanLayout.createSequentialGroup()
                            .addComponent(jTextFieldTimTaiKhoan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jToggleButtonTimTaiKhoan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonResetTaiKhoan))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldTaiKhoanPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelTaiKhoanLayout.createSequentialGroup()
                            .addComponent(jButtonSuaTaiKhoan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonXoaTaiKhoan)
                            .addGap(95, 95, 95)
                            .addComponent(jButtonThemTaiKhoan)))
                    .addComponent(jTextFieldTaiKhoanUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTaiKhoanTen, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTaiKhoanSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTaiKhoanEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTaiKhoanLayout.setVerticalGroup(
            jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
            .addGroup(jPanelTaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonTimTaiKhoan)
                    .addComponent(jButtonResetTaiKhoan))
                .addGap(38, 38, 38)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanTen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonThemTaiKhoan)
                    .addComponent(jButtonSuaTaiKhoan)
                    .addComponent(jButtonXoaTaiKhoan))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabMain.addTab("Tài Khoản", jPanelTaiKhoan);

        jPanel1.setBackground(new java.awt.Color(255, 51, 0));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Booking Table");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChonThanhPho
        ChonThanhPho();
    }//GEN-LAST:event_ChonThanhPho

    private void jButtonThemThanhPhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemThanhPhoActionPerformed
        ThemThanhPho();
    }//GEN-LAST:event_jButtonThemThanhPhoActionPerformed

    private void jButtonSuaThanhPhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaThanhPhoActionPerformed
        SuaThanhPho();
    }//GEN-LAST:event_jButtonSuaThanhPhoActionPerformed

    private void jButtonXoaThanhPhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaThanhPhoActionPerformed
        XoaThanhPho();
    }//GEN-LAST:event_jButtonXoaThanhPhoActionPerformed

    private void jToggleButtonTimThanhPhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTimThanhPhoActionPerformed
        TimThanhPho();
    }//GEN-LAST:event_jToggleButtonTimThanhPhoActionPerformed

    private void jButtonResetThanhPhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetThanhPhoActionPerformed
        LoadThanhPho();
    }//GEN-LAST:event_jButtonResetThanhPhoActionPerformed

    private void jTableKhuVucChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableKhuVucChonThanhPho
        ChonKhuVuc();
    }//GEN-LAST:event_jTableKhuVucChonThanhPho

    private void jToggleButtonTimKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTimKhuVucActionPerformed
        TimKhuVuc();
    }//GEN-LAST:event_jToggleButtonTimKhuVucActionPerformed

    private void jButtonResetKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetKhuVucActionPerformed
        LoadKhuVuc();
    }//GEN-LAST:event_jButtonResetKhuVucActionPerformed

    private void jButtonThemKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemKhuVucActionPerformed
        ThemKhuVuc();
    }//GEN-LAST:event_jButtonThemKhuVucActionPerformed

    private void jButtonSuaKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaKhuVucActionPerformed
        SuaKhuVuc();
    }//GEN-LAST:event_jButtonSuaKhuVucActionPerformed

    private void jButtonXoaKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaKhuVucActionPerformed
        XoaKhuVuc();
    }//GEN-LAST:event_jButtonXoaKhuVucActionPerformed

    private void jTableTaiKhoanChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTaiKhoanChonThanhPho
        ChonTaiKhoan();
    }//GEN-LAST:event_jTableTaiKhoanChonThanhPho

    private void jToggleButtonTimTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTimTaiKhoanActionPerformed
        TimTaiKhoan();
    }//GEN-LAST:event_jToggleButtonTimTaiKhoanActionPerformed

    private void jButtonResetTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetTaiKhoanActionPerformed
        LoadTaiKhoan();
    }//GEN-LAST:event_jButtonResetTaiKhoanActionPerformed

    private void jButtonThemTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemTaiKhoanActionPerformed
        ThemTaiKhoan();
    }//GEN-LAST:event_jButtonThemTaiKhoanActionPerformed

    private void jButtonSuaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaTaiKhoanActionPerformed
        SuaTaiKhoan();
    }//GEN-LAST:event_jButtonSuaTaiKhoanActionPerformed

    private void jButtonXoaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaTaiKhoanActionPerformed
        XoaTaiKhoan();
    }//GEN-LAST:event_jButtonXoaTaiKhoanActionPerformed

    private void jTableNhaHangChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableNhaHangChonThanhPho
        ChonNhaHang();
        if(evt.getClickCount()==2){
            ShowNhaHangDetail();
        }
    }//GEN-LAST:event_jTableNhaHangChonThanhPho

    private void jButtonUploadNhaHangHinhAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUploadNhaHangHinhAnhActionPerformed
        UploadHinhAnhNhaHang();
    }//GEN-LAST:event_jButtonUploadNhaHangHinhAnhActionPerformed

    private void jButtonThemNhaHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemNhaHangActionPerformed
        ThemNhaHang();
    }//GEN-LAST:event_jButtonThemNhaHangActionPerformed

    private void jButtonSuaNhaHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaNhaHangActionPerformed
        SuaNhaHang();
    }//GEN-LAST:event_jButtonSuaNhaHangActionPerformed

    private void jButtonXoaNhaHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaNhaHangActionPerformed
        XoaNhaHang();
    }//GEN-LAST:event_jButtonXoaNhaHangActionPerformed

    private void jButtonTimNhaHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimNhaHangActionPerformed
        TimNhaHang();
    }//GEN-LAST:event_jButtonTimNhaHangActionPerformed

    private void jButtonResetNhaHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetNhaHangActionPerformed
        LoadNhaHang();
    }//GEN-LAST:event_jButtonResetNhaHangActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonResetKhuVuc;
    private javax.swing.JButton jButtonResetNhaHang;
    private javax.swing.JButton jButtonResetTaiKhoan;
    private javax.swing.JButton jButtonResetThanhPho;
    private javax.swing.JButton jButtonSuaKhuVuc;
    private javax.swing.JButton jButtonSuaNhaHang;
    private javax.swing.JButton jButtonSuaTaiKhoan;
    private javax.swing.JButton jButtonSuaThanhPho;
    private javax.swing.JButton jButtonThemKhuVuc;
    private javax.swing.JButton jButtonThemNhaHang;
    private javax.swing.JButton jButtonThemTaiKhoan;
    private javax.swing.JButton jButtonThemThanhPho;
    private javax.swing.JButton jButtonTimNhaHang;
    private javax.swing.JButton jButtonUploadNhaHangHinhAnh;
    private javax.swing.JButton jButtonXoaKhuVuc;
    private javax.swing.JButton jButtonXoaNhaHang;
    private javax.swing.JButton jButtonXoaTaiKhoan;
    private javax.swing.JButton jButtonXoaThanhPho;
    private javax.swing.JComboBox jComboBoxKhuVuc_ThanhPho;
    private javax.swing.JComboBox jComboBoxNhaHang_KhuVuc;
    private javax.swing.JComboBox jComboBoxNhaHang_TaiKhoan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelNhaHangHinhAnh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelKhuVuc;
    private javax.swing.JPanel jPanelNhaHang;
    private javax.swing.JPanel jPanelTaiKhoan;
    private javax.swing.JPanel jPanelThanhPho;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableKhuVuc;
    private javax.swing.JTable jTableNhaHang;
    private javax.swing.JTable jTableTaiKhoan;
    private javax.swing.JTable jTableThanhPho;
    private javax.swing.JTextArea jTextAreaNhaHangMoTa;
    private javax.swing.JTextField jTextFieldKhuVucTen;
    private javax.swing.JTextField jTextFieldNhaHangDiaChi;
    private javax.swing.JTextField jTextFieldNhaHangGioDonKhach;
    private javax.swing.JTextField jTextFieldNhaHangKhoangTien;
    private javax.swing.JTextField jTextFieldNhaHangLoaiHinh;
    private javax.swing.JTextField jTextFieldNhaHangSdt;
    private javax.swing.JTextField jTextFieldNhaHangTen;
    private javax.swing.JTextField jTextFieldNhaHangUuDai;
    private javax.swing.JTextField jTextFieldTaiKhoanEmail;
    private javax.swing.JTextField jTextFieldTaiKhoanPassword;
    private javax.swing.JTextField jTextFieldTaiKhoanSdt;
    private javax.swing.JTextField jTextFieldTaiKhoanTen;
    private javax.swing.JTextField jTextFieldTaiKhoanUsername;
    private javax.swing.JTextField jTextFieldThanhPhoTen;
    private javax.swing.JTextField jTextFieldTimKhuVuc;
    private javax.swing.JTextField jTextFieldTimNhaHang;
    private javax.swing.JTextField jTextFieldTimTaiKhoan;
    private javax.swing.JTextField jTextFieldTimThanhPho;
    private javax.swing.JToggleButton jToggleButtonTimKhuVuc;
    private javax.swing.JToggleButton jToggleButtonTimTaiKhoan;
    private javax.swing.JToggleButton jToggleButtonTimThanhPho;
    private javax.swing.JTabbedPane tabMain;
    // End of variables declaration//GEN-END:variables
}
