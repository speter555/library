package com.baeldung.library.dto;

/**
 * Paging dto
 * 
 * @param pageNumber
 *            paging number of page
 * @param pageSize
 *            page size
 * 
 * @author speter555
 * @since 0.1.0
 */
public record PagingDto(int pageNumber, int pageSize) {

}
