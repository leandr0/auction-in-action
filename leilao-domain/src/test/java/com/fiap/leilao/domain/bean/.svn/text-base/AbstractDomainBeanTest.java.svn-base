/**
 * 
 */
package com.fiap.leilao.domain.bean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static junit.framework.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import com.fiap.leilao.domain.EntityBasic;

/**
 * @author Leandro
 *
 */
@SuppressWarnings({"all","rawtypes"})
public class AbstractDomainBeanTest {

	AbstractDomainBean	abstractDomainBeanMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		abstractDomainBeanMock 	= new ManagerLanceBean();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.fiap.leilao.domain.bean.AbstractDomainBean#insert(com.fiap.leilao.domain.EntityBasic)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInsertEntityNull() {
		try{
			abstractDomainBeanMock.insert(null);
		}catch (IllegalArgumentException e) {
			throw e;
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		fail();
	}

	@Test
	public void testInsertEntity() {

		EntityBasic entityBasicMock = mock(EntityBasic.class);
		abstractDomainBeanMock 		= mock(AbstractDomainBean.class);
		try{
			when(abstractDomainBeanMock.insert(entityBasicMock)).thenReturn(entityBasicMock);
			abstractDomainBeanMock.insert(entityBasicMock);
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.fiap.leilao.domain.bean.AbstractDomainBean#delete(com.fiap.leilao.domain.EntityBasic)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteEntityNull() {
		try{
			abstractDomainBeanMock.delete(null);
		}catch (IllegalArgumentException e) {
			throw e;
		}
		catch (Exception e) {
			fail(e.getMessage());
		}	
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteEntityIdNull() {
		EntityBasic entityBasicMock = mock(EntityBasic.class);
		when(entityBasicMock.getId()).thenReturn(null);
		try{
			abstractDomainBeanMock.delete(entityBasicMock);
		}catch (IllegalArgumentException e) {
			throw e;
		}
		catch (Exception e) {
			fail(e.getMessage());
		}	
		fail();
	}

	@Test
	public void testDelete() {
		EntityBasic entityBasicMock = mock(EntityBasic.class);
		abstractDomainBeanMock 		= mock(AbstractDomainBean.class);
		try{
			abstractDomainBeanMock.delete(entityBasicMock);
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.fiap.leilao.domain.bean.AbstractDomainBean#find(com.fiap.leilao.domain.EntityBasic)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFindEntityNull() {
		try{
			abstractDomainBeanMock.find(null);
		}catch (IllegalArgumentException e) {
			throw e;
		}
		catch (Exception e) {
			fail(e.getMessage());
		}	
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindEntityIdNull() {
		EntityBasic entityBasicMock = mock(EntityBasic.class);
		when(entityBasicMock.getId()).thenReturn(null);
		try{
			abstractDomainBeanMock.find(entityBasicMock);
		}catch (IllegalArgumentException e) {
			throw e;
		}catch (Exception e) {
			fail(e.getMessage());
		}	
		fail();
	}

	@Test
	public void testFind() {
		EntityBasic entityBasicMock = mock(EntityBasic.class);
		abstractDomainBeanMock 		= mock(AbstractDomainBean.class);
		try{
			abstractDomainBeanMock.find(entityBasicMock);
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.fiap.leilao.domain.bean.AbstractDomainBean#update(com.fiap.leilao.domain.EntityBasic)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateEntityNull() {
		try{
			abstractDomainBeanMock.update(null);
		}catch (IllegalArgumentException e) {
			throw e;
		}catch (Exception e) {
			fail(e.getMessage());
		}	
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateEntityIdNull() {
		EntityBasic entityBasicMock = mock(EntityBasic.class);
		when(entityBasicMock.getId()).thenReturn(null);
		try{
			abstractDomainBeanMock.update(entityBasicMock);
		}catch (IllegalArgumentException e) {
			throw e;
		}catch (Exception e) {
			fail(e.getMessage());
		}
		fail();
	}

	@Test
	public void testUpdate() {
		EntityBasic entityBasicMock = mock(EntityBasic.class);
		abstractDomainBeanMock 		= mock(AbstractDomainBean.class);
		try{
			abstractDomainBeanMock.update(entityBasicMock);
		}catch (Exception e) {
			fail(e.getMessage());
		}	
	}
}