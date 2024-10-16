package com.chat.model;

import java.util.*;


public interface ChatDAO_interface {
          public void insert(ChatVO chatVO);
          public List<ChatVO> findByMembIdAndConvContent(Integer memb_id, String conv_content);
          public List<ChatVO> findByMembId(Integer memb_id);
          public List<ChatVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<ChatVO> getAll(Map<String, String[]> map); 
}

