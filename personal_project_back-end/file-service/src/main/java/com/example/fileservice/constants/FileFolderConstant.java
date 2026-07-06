package com.example.fileservice.constants;

public class FileFolderConstant {
    private FileFolderConstant() {
    }
    public static final String prefix = "/";
    public static final String UPLOAD_FOLDER = "/upload";
    public static final String TEMPLATE_FOLDER = "/template";
    public static final String USER_UPLOAD_FOLDER = UPLOAD_FOLDER+"/user";
    public static final String CATEGORY_UPLOAD_FOLDER = UPLOAD_FOLDER+"/category";
    public static final String EXCEL_TEMPLATE_FOLDER = TEMPLATE_FOLDER+"/excel";
    public static final String PDF_TEMPLATE_FOLDER = TEMPLATE_FOLDER+"/pdf";
}
