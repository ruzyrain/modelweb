package HttpServer;
/*
 * ȫ�����Ͷ���
 */
public interface ConstantInterface {
	String JOININ_HEADER="2";
	byte POSITIONING_HEADER='9';
	String OUTTAKEN_HEADER="3";
	/*
	 * MD package
	 */
	int TYPE1_POS=53;
	int TYPE2_POS=54;
	// Edit By TaoYe
	/**
	 * ���ؽڵ��ų���
	 */
	int GATEWY_GID_LEN=16;
	/**
	 * С�ڵ�����ʼλ��,�����������ı�ʶ��ʼλ��
	 */
	int BLUE_MD_POS=37;
	/**
	 * С�ڵ��� ����
	 */
	int MD_GID_LEN=16;
	
	/**
	 * ʧ������ʾλ��ʼλ��
	 */
	int MISS_POS=53;
	
	/**
	 * ����С�ڵ����ʼ4λ������
	 */
	String BLUE_MDID_MARK="####";
	
	/**
	 * �ֻ�����ʧ������ʶ
	 */
	String BLUE_MISS_MARK="ge";
	
	/**
	 * �ֻ�������������ʶ
	 */
	String BLUE_RECONNECT_MARK="gd";
	
	/**
	 * ���ؽڵ�����������
	 */
	int GATEWY_HEARTBEAT_LENGTH=39;
	
	/**
	 * ���ؽڵ���������ʶ
	 */
	String GATEWAYHEARTBEAT_HEADER="7";
	
	/**
	 * С�ڵ���������ʶ
	 */
	char MD_HEATBEAT='a';
	
	/**
	 * ���͵���ҵ�������IP
	 * Ŀǰ�汾��Ф��
	 */
	String SERVERID="10.10.12.67";
	
	/**
	 * ���͵��Ķ�λ������IP
	 * Ŀǰ�汾����ұ
	 */
	String SERVICEID="10.10.12.164";
	
}
