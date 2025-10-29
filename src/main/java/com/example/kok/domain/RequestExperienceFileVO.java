package com.example.kok.domain;

import com.example.kok.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper=true)
@EqualsAndHashCode(of="fileId", callSuper = false)
@SuperBuilder
public class RequestExperienceFileVO extends Period{
    private long fileId;
    private long requestExperienceId;
}
