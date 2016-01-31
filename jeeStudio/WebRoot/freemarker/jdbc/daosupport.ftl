package ${package}.dao;

import javax.sql.DataSource;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;
import com.zxt.framework.jdbc.ZXTJDBCDataSource;


public class ZXTJDBCDaoSupport {
	
	public ZXTJDBCDaoSupport()
    {
    }

    public final void setDataSource(DataSource dataSource)
    {
        if(zxtJDBCTemplate == null || dataSource != zxtJDBCTemplate.getDataSource())
        {
        	zxtJDBCTemplate = createJdbcTemplate(dataSource);
            initTemplateConfig();
        }
    }

    protected ZXTJDBCTemplate createJdbcTemplate(DataSource dataSource)
    {
        return new ZXTJDBCTemplate(dataSource);
    }

    public final DataSource getDataSource()
    {
        return zxtJDBCTemplate == null ? null : zxtJDBCTemplate.getDataSource();
    }
    
    public final void setJdbcTemplate(ZXTJDBCTemplate zxtJDBCTemplate)
    {
        this.zxtJDBCTemplate = zxtJDBCTemplate;
        initTemplateConfig();
    }

    public final ZXTJDBCTemplate getJdbcTemplate()
    {
        return zxtJDBCTemplate;
    }
    <#list datasources as ds>
    public final ZXTJDBCTemplate get${ds.sid}Template()
    {
    	return new ZXTJDBCTemplate(new ZXTJDBCDataSource("${ds.ipAddress}","${ds.port}","${ds.sid}","${ds.dbType}","${ds.username}","${ds.password}"));
    }
    </#list>

    protected void initTemplateConfig()
    {
    }

    protected void checkDaoConfig()
    {
        if(zxtJDBCTemplate == null)
            throw new IllegalArgumentException("'dataSource' or 'jdbcTemplate' is required");
        else
            return;
    }

    private ZXTJDBCTemplate zxtJDBCTemplate;
}
