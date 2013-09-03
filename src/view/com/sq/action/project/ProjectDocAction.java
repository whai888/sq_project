package com.sq.action.project;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sq.action.system.PageAction;
import com.sq.exception.SystemException;
import com.sq.logic.project.IProjectDocService;
import com.sq.logic.tools.IUserLog;
import com.sq.model.vo.SqDocmentManager;
import com.sq.model.vo.SqProjectStep;
import com.sq.model.vo.SqUserTab;
import com.sq.tools.Public;
import com.sq.tools.WordBean;
import com.sq.vo.ErrorForm;

/**
 * 附:contentType类型.
'ez' => 'application/andrew-inset', 
'hqx' => 'application/mac-binhex40', 
'cpt' => 'application/mac-compactpro', 
'doc' => 'application/msword', 
'bin' => 'application/octet-stream', 
'dms' => 'application/octet-stream', 
'lha' => 'application/octet-stream', 
'lzh' => 'application/octet-stream', 
'exe' => 'application/octet-stream', 
'class' => 'application/octet-stream', 
'so' => 'application/octet-stream', 
'dll' => 'application/octet-stream', 
'oda' => 'application/oda', 
'pdf' => 'application/pdf', 
'ai' => 'application/postscript', 
'eps' => 'application/postscript', 
'ps' => 'application/postscript', 
'smi' => 'application/smil', 
'smil' => 'application/smil', 
'mif' => 'application/vnd.mif', 
'xls' => 'application/vnd.ms-excel', 
'ppt' => 'application/vnd.ms-powerpoint', 
'wbxml' => 'application/vnd.wap.wbxml', 
'wmlc' => 'application/vnd.wap.wmlc', 
'wmlsc' => 'application/vnd.wap.wmlscriptc', 
'bcpio' => 'application/x-bcpio', 
'vcd' => 'application/x-cdlink', 
'pgn' => 'application/x-chess-pgn', 
'cpio' => 'application/x-cpio', 
'csh' => 'application/x-csh', 
'dcr' => 'application/x-director', 
'dir' => 'application/x-director', 
'dxr' => 'application/x-director', 
'dvi' => 'application/x-dvi', 
'spl' => 'application/x-futuresplash', 
'gtar' => 'application/x-gtar', 
'hdf' => 'application/x-hdf', 
'js' => 'application/x-javas

cript', 
'skp' => 'application/x-koan', 
'skd' => 'application/x-koan', 
'skt' => 'application/x-koan', 
'skm' => 'application/x-koan', 
'latex' => 'application/x-latex', 
'nc' => 'application/x-netcdf', 
'cdf' => 'application/x-netcdf', 
'sh' => 'application/x-sh', 
'shar' => 'application/x-shar', 
'swf' => 'application/x-shockwave-flash', 
'sit' => 'application/x-stuffit', 
'sv4cpio' => 'application/x-sv4cpio', 
'sv4crc' => 'application/x-sv4crc', 
'tar' => 'application/x-tar', 
'tcl' => 'application/x-tcl', 
'tex' => 'application/x-tex', 
'texinfo' => 'application/x-texinfo', 
'texi' => 'application/x-texinfo', 
't' => 'application/x-troff', 
'tr' => 'application/x-troff', 
'roff' => 'application/x-troff', 
'man' => 'application/x-troff-man', 
'me' => 'application/x-troff-me', 
'ms' => 'application/x-troff-ms', 
'ustar' => 'application/x-ustar', 
'src' => 'application/x-wais-source', 
'xhtml' => 'application/xhtml+xml', 
'xht' => 'application/xhtml+xml', 
'zip' => 'application/zip', 
'au' => 'audio/basic', 
'snd' => 'audio/basic', 
'mid' => 'audio/midi', 
'midi' => 'audio/midi', 
'kar' => 'audio/midi', 
'mpga' => 'audio/mpeg', 
'mp2' => 'audio/mpeg', 
'mp3' => 'audio/mpeg', 
'aif' => 'audio/x-aiff', 
'aiff' => 'audio/x-aiff', 
'aifc' => 'audio/x-aiff', 
'm3u' => 'audio/x-mpegurl', 
'ram' => 'audio/x-pn-realaudio', 
'rm' => 'audio/x-pn-realaudio', 
'rpm' => 'audio/x-pn-realaudio-plugin', 
'ra' => 'audio/x-realaudio', 
'wav' => 'audio/x-wav', 
'pdb' => 'chemical/x-pdb', 
'xyz' => 'chemical/x-xyz', 
'bmp' => 'image/bmp', 
'gif' => 'image/gif', 
'ief' => 'image/ief', 
'jpeg' => 'image/jpeg', 
'jpg' => 'image/jpeg', 
'jpe' => 'image/jpeg', 
'png' => 'image/png', 
'tiff' => 'image/tiff', 
'tif' => 'image/tiff', 
'djvu' => 'image/vnd.djvu', 
'djv' => 'image/vnd.djvu', 
'wbmp' => 'image/vnd.wap.wbmp', 
'ras' => 'image/x-cmu-raster', 
'pnm' => 'image/x-portable-anymap', 
'pbm' => 'image/x-portable-bitmap', 
'pgm' => 'image/x-portable-graymap', 
'ppm' => 'image/x-portable-pixmap', 
'rgb' => 'image/x-rgb', 
'xbm' => 'image/x-xbitmap', 
'xpm' => 'image/x-xpixmap', 
'xwd' => 'image/x-xwindowdump', 
'igs' => 'model/iges', 
'iges' => 'model/iges', 
'msh' => 'model/mesh', 
'mesh' => 'model/mesh', 
'silo' => 'model/mesh', 
'wrl' => 'model/vrml', 
'vrml' => 'model/vrml', 
'css' => 'text/css', 
'html' => 'text/html', 
'htm' => 'text/html', 
'asc' => 'text/plain', 
'txt' => 'text/plain', 
'rtx' => 'text/richtext', 
'rtf' => 'text/rtf', 
'sgml' => 'text/sgml', 
'sgm' => 'text/sgml', 
'tsv' => 'text/tab-separated-values', 
'wml' => 'text/vnd.wap.wml', 
'wmls' => 'text/vnd.wap.wmlscript', 
'etx' => 'text/x-setext', 
'xsl' => 'text/xml', 
'xml' => 'text/xml', 
'mpeg' => 'video/mpeg', 
'mpg' => 'video/mpeg', 
'mpe' => 'video/mpeg', 
'qt' => 'video/quicktime', 
'mov' => 'video/quicktime', 
'mxu' => 'video/vnd.mpegurl', 
'avi' => 'video/x-msvideo', 
'movie' => 'video/x-sgi-movie', 
'ice' => 'x-conference/x-cooltalk'
 * @author whai
 *
 */
public class ProjectDocAction extends PageAction{
	@Resource
	private IProjectDocService iProjectDocService ;
	@Resource
	private IUserLog iUserLog ;
	private ErrorForm errorForm = new ErrorForm();
	private SqProjectStep sqProjectStep ;
	private SqDocmentManager sqDocmentManager ;
	private String returnPage ;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession(false);
	
	public String projectDocUpload(){
		try {
			this.projectDocUpload(sqDocmentManager);
			//保存上传的文件信息
			SqUserTab sqUserTabTemp = (SqUserTab) session.getAttribute("sqUserTab");
			sqDocmentManager.setUserId(sqUserTabTemp.getUserId());
			sqDocmentManager.setFilePath(directory) ;
			sqDocmentManager.setBackfileName(fileNameFileName);
			sqDocmentManager.setFileName(targetFileName) ;
			sqDocmentManager.setStatus("0");
			if(!Public.isEmpty(sqDocmentManager.getRemark1()))
				sqDocmentManager.setRemark1(sqUserTabTemp.getSqGroupTab().getGroupNo() + sqDocmentManager.getRemark1());
			iProjectDocService.saveDocManager(sqDocmentManager );
			
		} catch (SystemException e) {
			iUserLog.logwrite(request, e);
			errorForm.setMessage(e.getMessage());
			return "error" ;
		}
		return this.returnPage;
	}
	
	/**
	 * 根据文档编号删除对应的文档
	 * @return
	 */
	public String deleteDocManager(){
		iProjectDocService.deleteDocManager(sqDocmentManager);
		return this.returnPage ;
	}
	
	/**
	 * 修改文档对应的状态
	 * @return
	 */
	public String projectDocUpdate(){
		iProjectDocService.updateDocManager(sqDocmentManager);
		return "projectdocupload";
	}

	/**
	 * 文件下载
	 * @return
	 */
	public String projectDocDown(){
		return "success";
	}
	
	/**
	 * 周报文件下载
	 * @return
	 */
	public String workDayDocDown(){
		return "success";
	}
	
	public SqDocmentManager getSqDocmentManager() {
		return sqDocmentManager;
	}

	public void setSqDocmentManager(SqDocmentManager sqDocmentManager) {
		this.sqDocmentManager = sqDocmentManager;
	}

	public SqProjectStep getSqProjectStep() {
		return sqProjectStep;
	}

	public void setSqProjectStep(SqProjectStep sqProjectStep) {
		this.sqProjectStep = sqProjectStep;
	}

	public ErrorForm getErrorForm() {
		return errorForm;
	}

	public void setErrorForm(ErrorForm errorForm) {
		this.errorForm = errorForm;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}

	
}
