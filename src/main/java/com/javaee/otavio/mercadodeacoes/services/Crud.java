package com.javaee.otavio.mercadodeacoes.services;

import java.util.Set;

public interface Crud<T> {
	Set<T> getAll();
	T findById(String id);
	T createNew(T item);
	T save(T item);
	void deleteById(String id);
}
