package dao;

import java.util.List;

import table.BanJi;

public interface BanJiDao {
	/*
	 * ��ȡ���еİ༶��Ϣ
	 */
	public List<BanJi> getBanJi();
	/**
	 * ����༶
	 */
	public int insertBanJi(BanJi bj);
	/**
	 * �޸İ༶
	 */
	public int updateBanJi(BanJi bj);
	/**
	 * ɾ���༶
	 */
	public int deleteBanJi(int bjid);
	/**
	 * ���հ༶���Ʋ���ID
	 */
	public int getIdByName(String bjName);
}
