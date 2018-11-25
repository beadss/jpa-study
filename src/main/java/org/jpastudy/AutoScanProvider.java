package org.jpastudy;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * org.jpastudy 패키지 아래의 모든 Entity 클래스들을 스캐닝해주는 Provider입니다
 * 스프링 안붙이면 스캐닝이 안돼서 대충 만들어뒀습니다
 */
public class AutoScanProvider extends HibernatePersistenceProvider {
	public EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) {
		return createEntityManagerFactory(persistenceUnitName, null);
	}

	@Override
	protected EntityManagerFactoryBuilder getEntityManagerFactoryBuilder(PersistenceUnitDescriptor persistenceUnitDescriptor,
																		 Map integration, ClassLoader providedClassLoader) {

		((ParsedPersistenceXmlDescriptor)persistenceUnitDescriptor).addClasses(getAllClasses("org.jpastudy"));
		return Bootstrap.getEntityManagerFactoryBuilder( persistenceUnitDescriptor, integration, providedClassLoader );
	}

	@Override
	protected EntityManagerFactoryBuilder getEntityManagerFactoryBuilder(PersistenceUnitDescriptor persistenceUnitDescriptor,
																		 Map integration, ClassLoaderService providedClassLoaderService) {
		((ParsedPersistenceXmlDescriptor)persistenceUnitDescriptor).addClasses(getAllClasses("org.jpastudy"));
		return Bootstrap.getEntityManagerFactoryBuilder( persistenceUnitDescriptor, integration, providedClassLoaderService );
	}

	public List<String> getAllClasses(String pckgname) {
		try{
			// Get a File object for the package
			File directory=null;
			try {
				directory=new File(Thread.currentThread().getContextClassLoader().getResource(pckgname.replace('.', '/')).getFile());
			} catch(NullPointerException x) {
				System.out.println("Nullpointer");
				throw new ClassNotFoundException(pckgname+" does not appear to be a valid package");
			}

			return getAllClasses(pckgname, directory);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getAllClasses(String packageName, File directory) {

		try{
			List<String> classes = new ArrayList<>();
			if(directory.exists()) {
				// Get the list of the files contained in the package
				File[] files=directory.listFiles();
				for(int i=0; i<files.length; i++) {
					File file = files[i];
					if(file.isDirectory()) {
						classes.addAll(getAllClasses(packageName + "." + file.getName(), file));
					} else if(files[i].getName().endsWith(".class")) {
						String fileName = file.getName();
						// removes the .class extension
						Class<?> clazz = Class.forName(packageName+'.'+ fileName.substring(0, fileName.length()-6));
						if(clazz.getAnnotation(Entity.class) != null) {
							classes.add(clazz.getName());
						}

					}
				}
			} else {
				System.out.println("Directory does not exist");
				throw new ClassNotFoundException(packageName+" does not appear to be a valid package");
			}

			return classes;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
