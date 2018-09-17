package com.perscholas.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

 
 
public class PaginationResult<E> {
 
	static Logger log = Logger.getLogger(PaginationResult.class);
	
   private int totalRecords;
   private int currentPage;
   private List<E> list;
   private int maxResult;
   private int totalPages;
 
   private int maxNavigationPage;
 
   private List<Integer> navigationPages;
 
   // @page: 1, 2, ..
   public PaginationResult(List<E> queryList, int page, int maxResult, int maxNavigationPage) {
	   final int pageIndex = page - 1 < 0 ? 0 : page - 1;
	   
       int fromRecordIndex = pageIndex * maxResult;
       int maxRecordIndex = fromRecordIndex + maxResult;

       this.totalRecords = queryList.size();
       
       log.debug("totalRecords: " + totalRecords);
       log.debug("fromRecordIndex: " + fromRecordIndex);
       log.debug("maxRecordIndex: " + maxRecordIndex);
       
       
       if (maxRecordIndex > this.totalRecords) {
    	   maxRecordIndex = this.totalRecords;
       }
 
       List<E> results = new ArrayList<E>();
       
       results = queryList.subList(fromRecordIndex, maxRecordIndex);
              
       
       this.currentPage = pageIndex + 1;
       this.list = results;
       this.maxResult = maxResult;
 
       this.totalPages = (this.totalRecords / this.maxResult);
       
       log.debug("totalPages: " + totalPages);
       
       log.debug("this.totalRecords % this.maxResult: " + this.totalRecords % this.maxResult);
       
       if (this.totalRecords % this.maxResult > 0) {
    	   this.totalPages = this.totalPages + 1;
       }
       
       log.debug("totalPages: " + totalPages);
       
        
       if (maxNavigationPage > totalPages) {
           this.maxNavigationPage = totalPages;
       } else {
    	   this.maxNavigationPage = maxNavigationPage;
       }
       
       log.debug("this.maxNavigationPage: " + this.maxNavigationPage);
 
       this.calcNavigationPages();
   }
 
   private void calcNavigationPages() {
 
	   this.navigationPages = new ArrayList<Integer>();
	   
       int current = this.currentPage > this.totalPages ? this.totalPages : this.currentPage;
       log.debug("current: " + current);
 
       int begin = current - (this.maxNavigationPage - 2) / 2;
       log.debug("begin: " + begin);
       
       int end = current + (this.maxNavigationPage - 2) / 2;
       log.debug("end: " + end);
       
       int left = 0, right = 0;
       
       if (begin < 2) {
    	   left = 2 - begin;
       }
       log.debug("left: " + left);
       
       if (end > this.totalPages - 1) {
    	   right = end - (this.totalPages - 1);
       }
       log.debug("right: " + right);
       
       begin -= right;
       log.debug("begin: " + begin);
       
       end += left;
       log.debug("end: " + end);
 
       // First page
       navigationPages.add(1);
       
       if (begin > 2) {
           // For '...'
           navigationPages.add(-1);
       }
 
       for (int i = begin; i <= end; i++) {
           if (i > 1 && i < this.totalPages) {
               navigationPages.add(i);
           }
       }
 
       if (end < this.totalPages - 1) {
           // For '...'
           navigationPages.add(-1);
       }
       // Last page.
       navigationPages.add(this.totalPages);
   }
 
   public int getTotalPages() {
       return totalPages;
   }
 
   public int getTotalRecords() {
       return totalRecords;
   }
 
   public int getCurrentPage() {
       return currentPage;
   }
 
   public List<E> getList() {
       return list;
   }
 
   public int getMaxResult() {
       return maxResult;
   }
 
   public List<Integer> getNavigationPages() {
       return navigationPages;
   }
  
}
