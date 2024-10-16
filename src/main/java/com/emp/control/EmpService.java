package com.emp.control;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.cipher.model.CipherInterface;
import com.cipher.model.KeyFormatInterface;
import com.emp.model.EmpDaoImpl;
import com.emp.model.EmpLoginVo;
import com.emp.model.EmpVo;
import com.laiutil.Tuple;
import com.laiutil.json.JsonDeserializerInterface;
import com.laiutil.jwt.JwtUtil;
import com.laiutil.vault.VaultFuntion;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kotlin.Pair;
import redis.clients.jedis.JedisPool;

public class EmpService implements KeyFormatInterface, CipherInterface, JwtUtil ,JsonDeserializerInterface{

	EmpDaoImpl eDaoImpl;
	VaultFuntion vFuntion;

	public EmpService() {
		eDaoImpl = new EmpDaoImpl();
	}

	public boolean formatCheck(EmpVo emp, String oupString) {
		emp.setAccount(emp.getAccount().trim());
		emp.setPassword(emp.getPassword().trim());
		emp.setName(emp.getName().trim());
		if (emp.getAccount().length() > 24) {
			oupString = "帳號大於24位";
			return false;
		} else if (emp.getPassword().length() > 24) {
			oupString = "密碼大於24位";
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String createJwt(Object emp, Pair<String, String> claim, long expirMillis) {
		Date now = new Date(System.currentTimeMillis());
		Date exp = new Date(System.currentTimeMillis() + expirMillis);
		Tuple<String, String, PrivateKey> loginData  = (Tuple<String, String, PrivateKey>)emp;
		try {

			String jws = Jwts.builder().setIssuer("TIA").setSubject(loginData.getK()).setIssuedAt(now)
					.setExpiration(exp).claim("role", "emp").signWith(loginData.getV2(), SignatureAlgorithm.RS256).compact();
			return jws;
		} catch (Exception e1) {
			e1.printStackTrace();
			return "";
		}
	}

	public boolean login(Tuple<String, String,PrivateKey> loginData, Map<String, String>vaultData) {
		//TODO 在優化

		Pair<String, PrivateKey> targetData = null;
		if (vaultData.containsKey(loginData.getK())) {
			try {
				targetData = new Pair<String, PrivateKey>(loginData.getK(),
						(PrivateKey) getRsaKeyFromBase64(vaultData.get(loginData.getK()), "private"));
				var dataBaseEmp = eDaoImpl.getByAccount(loginData.getK());
				dataBaseEmp.setPassword(decrypt(dataBaseEmp.getPassword(), targetData.getSecond(), RSA));

				EmpLoginVo login =  jsonToObject(decrypt(loginData.getV1(), targetData.getSecond(), RSA), EmpLoginVo.class);
				loginData.setV2(targetData.getSecond());
				return login.getPassword().equals(dataBaseEmp.getPassword())? true : false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;// 帳號輸入錯誤
			}
		} else {
			return false;// 帳號不存在
		}
	}

	public boolean isAccountDuplication(EmpVo emp) {

		return eDaoImpl.isAccountDuplication(emp.getAccount());
	}

	//TODO 專用excpion
	public String getEmpBase64PubKey(JedisPool jpool,String username)throws Exception {
		EmpVo emp = eDaoImpl.redisGetByKey(jpool, username);

		if(emp.getPublicKey().length()==0) {
			var data =  eDaoImpl.getByAccount(username);
			if(data==null)
				return "無此帳號";
			emp.setPublicKey(data.getPublicKey());
		}
		return emp.getPublicKey();
	}


	public List<EmpVo> getAllEmpData(){
		return eDaoImpl.getAll();
	}
	public boolean saveEmpDataToDataBase(EmpVo emp, PublicKey key) {

		emp.setPublicKey(getBase64FromKey(key));
		emp.setPassword(encrypt(emp.getPassword(), key, RSA));
		eDaoImpl.insert(emp);
		return true;
	}
	public void saveTokenToRedis(JedisPool jpool,Tuple<String, String, String> data, long expirMillis) {

		eDaoImpl.redisInsert(jpool,data,expirMillis);
	}
	public boolean saveEmpDataToRedis(JedisPool jpool, EmpVo emp, PublicKey key) {

		emp.setPublicKey(getBase64FromKey(key));
		Pair<String, Map<String, String>> data = new Pair<String, Map<String, String>>(emp.getAccount(),
				new HashMap<String, String>());

		data.getSecond().put("id", emp.getId().toString());
		data.getSecond().put("pubKey", emp.getPublicKey());
		data.getSecond().put("password", emp.getPassword());

		eDaoImpl.redisInsert(jpool, "emp:", data);
		return true;
	}

	// "keys/empKey"
	public boolean saveEmpDataToVault(EmpVo emp, PrivateKey key, Vault vault, String path) {
		vFuntion = new VaultFuntion(vault, path);
		try {
			vFuntion.update(new Pair<String, String>(emp.getAccount(), getBase64FromKey(key)));
			return true;
		} catch (VaultException e) {
			e.printStackTrace();
			return false;
		}
	}
}
