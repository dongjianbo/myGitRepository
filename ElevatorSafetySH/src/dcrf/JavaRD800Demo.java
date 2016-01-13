package dcrf;
/*
 * 本例程采用JAR的调用方式，包dcrf.jar需要加在JAVA的需求库路径中
 *
 * 
 * 
 * 
 * 
 * 
 * 
 *
 */


class JavaRD800Demo
{
	public static void main(String[] args)
	{
		short status;
		int lDevice = 0;
		int[] pSnr = new int[1];
		char[] pSBuffer = new char[16];
		char[] pRBuffer = new char[16];
		JavaRD800 rd = new JavaRD800();
		
		lDevice = rd.dc_init(0,115200);
		if(lDevice <= 0)
		{
			System.out.print("dc_init error!\n");
			return;
		}
		System.out.print("dc_init ok!\n");
		
		status = rd.dc_card(lDevice,(short)0,pSnr);
		if(status != 0)
		{
			System.out.print("dc_card error!\n");
			rd.dc_exit(lDevice);
			return;
		}
		System.out.print("dc_card ok!\n");
		System.out.println(pSnr[0]);
		
		pSBuffer[0] = 0xFF;
		pSBuffer[1] = 0xFF;
		pSBuffer[2] = 0xFF;
		pSBuffer[3] = 0xFF;
		pSBuffer[4] = 0xFF;
		pSBuffer[5] = 0xFF;
		pSBuffer[6] = 0x00;
		status = rd.dc_load_key(lDevice,(short)0,(short)0,pSBuffer);
		if(status != 0)
		{
			System.out.print("dc_load_key error!\n");
			rd.dc_exit(lDevice);
			return;
		}
		System.out.print("dc_load_key ok!\n");
		
		status = rd.dc_authentication(lDevice,(short)0,(short)0);
		if(status != 0)
		{
			System.out.print("dc_authentication error!\n");
			rd.dc_exit(lDevice);
			return;
		}
		System.out.print("dc_authentication ok!\n");
		
		for(int i=0;i<16;i++)
		{
			pSBuffer[i] = 'M';
		}
		status = rd.dc_write(lDevice,(short)2,pSBuffer);
		if(status != 0)
		{
			System.out.print("dc_write error!\n");
			rd.dc_exit(lDevice);
			return;
		}
		System.out.print("dc_write ok!\n");
		
		status = rd.dc_read(lDevice,(short)2,pRBuffer);
		if(status != 0)
		{
			System.out.print("dc_read error!\n");
			rd.dc_exit(lDevice);
			return;
		}
		System.out.print("dc_read ok!\n");
		System.out.println(pRBuffer);
		
		status = rd.dc_exit(lDevice);
		if(status != 0)
		{
			System.out.print("dc_exit error!\n");
			rd.dc_exit(lDevice);
			return;
		}
		System.out.print("dc_exit ok!\n");
	}
}
