package br.com.robsonlmds.fipecar.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public record modelos(List<Dados> modelos) {}
