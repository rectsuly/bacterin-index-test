/*
 * 药品疫苗应用系统索引测试
 * @author:sily
 * create at 2017.5.21
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class bacterin extends JFrame {
	
	private static final long serialVersionUID = -2270569976296891899L;
	private static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static String user="c##sily";
	private static String password = "Piequals31415926";
	public static Connection conn;
	public static PreparedStatement ps;
	public static ResultSet rs;
	public static Statement st;
	//单表查询
	private static String query_rt_putin_sub = "select * from RT_PUTINSTORE_SUB where 疫苗ID=1 and SerialNumber=25";
	private static String index_rt_putin_sub = "create index rtputin_sub_serialnum on RT_PUTINSTORE_SUB(SerialNumber)";
	private static String delete_index_rt_putin_sub = "drop index rtputin_sub_serialnum";
	private static String unique_index_rt_putin_sub = "create unique index unique_rtputin_sub_serialnum on RT_PUTINSTORE_SUB(疫苗ID，SerialNumber)";
	private static String delete_unique_index_rt_putin_sub = "drop index unique_rtputin_sub_serialnum";
	//多表查询
	private static String query_putin_putout_sub = "select * from RT_PUTINSTORE_SUB,RT_PUTOUTSTORE_SUB "
			+ "where RT_PUTOUTSTORE_SUB.SerialNumber=RT_PUTINSTORE_SUB.SerialNumber and RT_PUTOUTSTORE_SUB.疫苗ID=2";
	//多表查询的一般索引
	private static String index_rt_putout_sub = "create index rtputout_sub_serialnum on RT_PUTOUTSTORE_SUB(SerialNumber)";
	private static String delete_index_rt_putout_sub = "drop index rtputout_sub_serialnum";
	//多表查询建立的唯一性索引
	private static String unique_index_rt_putout_sub = "create unique index unique_rtputout_sub_serialnum on RT_PUTOUTSTORE_SUB(SerialNumber，疫苗ID)";
	private static String unique_index_rt_putin = "create unique index unique_rtputin_sub on RT_PUTINSTORE_SUB(SerialNumber，疫苗ID)";
	private static String delete_unique_index_rt_putout_sub = "drop index unique_rtputout_sub_serialnum";
	private static String delete_unique_index_rt_putin = "drop index unique_rtputin_sub";
	//显示查询时间信息的标签
	static JPanel panel = new JPanel();
	static JLabel JLabel1 = new JLabel();
	static JLabel JLabel2 = new JLabel();
	static JLabel JLabel3 = new JLabel();
	static JLabel JLabel4 = new JLabel();
	static JLabel JLabel5 = new JLabel();
	static JLabel JLabel6 = new JLabel();
	static JLabel JLabel7 = new JLabel();
	static JLabel JLabel8 = new JLabel();
	static JLabel JLabel9 = new JLabel();
	static JLabel JLabel10 = new JLabel();
	static JLabel JLabel11= new JLabel();
	static JLabel JLabel12 = new JLabel();
	static JLabel JLabel13 = new JLabel();
	//类的构造方法
	public bacterin(){
		this.init();		//初始化，绘制界面
	}
	public void init(){
		this.setTitle("疫苗应用系统索引测试");
		this.setBounds(100,100,340,500);
		this.createUI();	//绘制界面的方法
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void createUI(){
		
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder tBorder = BorderFactory.createTitledBorder(border,"查询面板",TitledBorder.CENTER
				,TitledBorder.TOP); 
		panel.setBorder(tBorder);
		panel.setLayout(null);
		this.add(panel);
		JButton btn = new JButton("查询并显示结果");		//添加查询按钮
		btn.setBounds(80, 50, 150, 22);
		btn.addActionListener(new ActionListener(){		//按钮的点击事件
			public void actionPerformed(ActionEvent arg0){
				test(query_rt_putin_sub);			//查询前的第一次缓冲测试
				System.out.println("未建立任何索引：");	
				JLabel1.setBounds(50, 90, 300, 100);
				panel.add(JLabel1);
				JLabel1.setText("未建立任何索引");
				
				JLabel2.setBounds(50, 100, 300, 100);
				panel.add(JLabel2);					//未建立索引的查询
				JLabel2.setText("查询执行时间： " + query(query_rt_putin_sub,2000) + "ms");
				
				System.out.println("\n建立一般索引：");
				createIndex(index_rt_putin_sub);
				JLabel3.setBounds(50, 120, 300, 100);
				panel.add(JLabel3);
				JLabel3.setText("建立一般索引");
				JLabel4.setBounds(50, 130, 300, 100);
				panel.add(JLabel4);					//建立一般索引后的查询
				JLabel4.setText("查询执行时间： " + query(query_rt_putin_sub,2000) + "ms");
				deleteIndex(delete_index_rt_putin_sub);
				
				System.out.println("\n建立唯一性索引：");
				createIndex(unique_index_rt_putin_sub);
				JLabel5.setBounds(50, 150, 300, 100);
				panel.add(JLabel5);
				JLabel5.setText("建立唯一性索引");
				JLabel6.setBounds(50, 160, 300, 100);
				panel.add(JLabel6);					//建立唯一性索引后的查询
				JLabel6.setText("查询执行时间： " + query(query_rt_putin_sub,2000) + "ms");
				deleteIndex(delete_unique_index_rt_putin_sub);
				//多表查询
				System.out.println("\n多表查询:");
				JLabel7.setBounds(50, 180, 300, 100);
				panel.add(JLabel7);
				JLabel7.setText("多表查询");
				test(query_putin_putout_sub);	//查询前的第一次缓冲测试
				
				System.out.println("\n未建立任何索引:");
				JLabel8.setBounds(50, 190, 300, 100);
				panel.add(JLabel8);
				JLabel8.setText("未建立任何索引");
				JLabel9.setBounds(50, 200, 300, 100);
				panel.add(JLabel9);				//未建立索引的查询
				JLabel9.setText("查询执行时间： " + query(query_putin_putout_sub,1000) + "ms");
				
				System.out.println("\n建立一般索引：");
				createIndex(index_rt_putin_sub);
				createIndex(index_rt_putout_sub);
				JLabel10.setBounds(50, 220, 300, 100);
				panel.add(JLabel10);
				JLabel10.setText("建立一般索引");
				JLabel11.setBounds(50, 230, 300, 100);
				panel.add(JLabel11);			//建立一般索引后的查询
				JLabel11.setText("查询执行时间： " + query(query_putin_putout_sub,1000) + "ms");
				deleteIndex(delete_index_rt_putin_sub);
				deleteIndex(delete_index_rt_putout_sub);
				
				System.out.println("\n建立唯一性索引：");
				createIndex(unique_index_rt_putout_sub);
				createIndex(unique_index_rt_putin);
				JLabel12.setBounds(50, 250, 300, 100);
				panel.add(JLabel12);
				JLabel12.setText("建立唯一性索引");
				JLabel13.setBounds(50, 260, 300, 100);
				panel.add(JLabel13);			//建立唯一性索引后的查询
				JLabel13.setText("查询执行时间： " + query(query_putin_putout_sub,1000) + "ms");
				deleteIndex(delete_unique_index_rt_putout_sub);
				deleteIndex(delete_unique_index_rt_putin);
			}
		});
		panel.add(btn);
		
		
	}
	//利用DriverManager的getConnection方法建立的连接数据库的URL对象
	public static void getConnection(){			
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//查询SQL语句的方法，循环执行上千次查询语句，返回查询所用的总时间
	public static long query(String query,int time){
		long startTime=0,endTime=0;
		getConnection();
		if(conn == null){
			System.out.println("与oracle数据库连接失败!");
		}else{
		//	System.out.println("与oracle数据库连接成功！");
		}
		try {
			startTime = System.currentTimeMillis(); // 获取开始时间
			
			for(int i=0; i<time; i++){
				
				st = conn.createStatement();
				rs = st.executeQuery(query);
			while(rs.next()){
				//打印输出查询结果，在此省略
//				System.out.println(((rs.getString(1)) != null?rs.getString(1):"-") + "\t"
//						+ ((rs.getString(2)) != null?rs.getString(2):"-" )+ "\t"
//						+ ((rs.getString(3)) != null?rs.getString(3):"-" )+ "\t"
//					);
			
			}
			rs.close();
			}
			endTime = System.currentTimeMillis(); // 获取结束时间  
			 System.out.println("查询执行时间： " + (endTime - startTime) + "ms"); 
			
			 conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (endTime - startTime);
	}
	//第一次循环前的测试数据库缓冲，以获得稳定的查询时间
	public static void test(String query){
		getConnection();
		try {
			for(int i=0; i<200; i++){
				st = conn.createStatement();
				rs = st.executeQuery(query);
			while(rs.next()){
				
			}
			rs.close();
			}
			 conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//创建索引的方法
	public static void createIndex(String index){
		getConnection();
		try {
			st = conn.createStatement();
			st.execute(index);
			conn.close();
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		System.out.println("索引已建立！");
	}
	
	//删除索引的方法
	public static void deleteIndex(String index){
		getConnection();
		try {
			st = conn.createStatement();
			st.execute(index);
			conn.close();
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		System.out.println("索引已删除！");
	}
	//main主函数，注释掉的语句为未使用Swing组件前的查询，通过控制台输出查询时间
	public static void main(String[] args) throws UnsupportedEncodingException{
		
		new bacterin();
	//	test(query_rt_putin_sub);
/*		System.out.println("未建立任何索引：");
		query(query_rt_putin_sub,2000);
		System.out.println("\n建立一般索引：");
		createIndex(index_rt_putin_sub);
		query(query_rt_putin_sub,2000);
		deleteIndex(delete_index_rt_putin_sub);
		System.out.println("\n建立唯一性索引：");
		createIndex(unique_index_rt_putin_sub);
		query(query_rt_putin_sub,2000);
		deleteIndex(delete_unique_index_rt_putin_sub);
		
		System.out.println("\n多表查询：");
		System.out.println("未建立任何索引：");
	//	test(query_putin_putout_sub);
		query(query_putin_putout_sub,1000);
		System.out.println("\n建立一般索引：");
	//	createIndex(index_rt_putin_sub);
		createIndex(index_rt_putout_sub);
		query(query_putin_putout_sub,1000);
	//	deleteIndex(delete_index_rt_putin_sub);
		deleteIndex(delete_index_rt_putout_sub);
		System.out.println("\n建立唯一性索引：");
	//	createIndex(unique_index_rt_putin_sub);
		createIndex(unique_index_rt_putout_sub);
		query(query_putin_putout_sub,1000);
	//	deleteIndex(delete_unique_index_rt_putin_sub);
		deleteIndex(delete_unique_index_rt_putout_sub); */

	}
}


