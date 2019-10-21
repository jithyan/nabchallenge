package com.jithyan.nabchallengebff.model;

import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A wrapper for when a response to the UI comprises of a list of records.
 * 
 * @author Jithya
 *
 * @param <T> Any class seriablizable to JSON
 */
@Getter
@EqualsAndHashCode
public class MultiRecordResponse<T> {
   private final int recordCount;
   private final List<T> records;

   public MultiRecordResponse(List<T> records) {
      if (records == null) {
         this.records = Collections.emptyList();
      } else {
         this.records = records;
      }

      this.recordCount = this.records.size();
   }

}
