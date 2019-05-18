package io.github.firefang.power.server.entity.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * @author xinufo
 *
 */
@Data
public class AssertionForm {
    @NotBlank
    @Length(max = 255)
    private String expression;
}
