package com.workFlow.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalResponse {
	
	private String message;
    private String error;
    private Object httpStatus;
    private Object data;

		public GlobalResponse(String message, String error, Object httpStatus, Object data) {
			super();
			this.message = message;
			this.error = error;
			this.httpStatus = httpStatus;
			this.data = data;
		}

		public GlobalResponse(String message, String error, Object httpStatus) {
			super();
			this.message = message;
			this.error = error;
			this.httpStatus = httpStatus;
			
		}

		public GlobalResponse(String message,Object httpStatus){
            this.message = message;
            this.httpStatus = httpStatus;

		}

}