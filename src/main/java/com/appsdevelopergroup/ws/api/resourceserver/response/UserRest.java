package com.appsdevelopergroup.ws.api.resourceserver.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRest {

    private String firstName;
    private String lastName;
    private String userid;

}
