package com.project.shopping.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

		private List<ProductDTO> productdetails;
		private int pageNumber;
		private int pageSize;
		private long totalElements;
		private long totalPages;
		private boolean lastPage;
}
