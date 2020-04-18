package tae.cosmetics;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import com.google.common.collect.Sets;
import com.matt.forgehax.util.classloader.AbstractClassLoader;
import com.matt.forgehax.util.classloader.ClassLoaderHelper;

import net.minecraftforge.common.MinecraftForge;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.mods.BaseMod;

public class ModLoader extends AbstractClassLoader<BaseMod> implements Globals {

	private Set<Class<? extends BaseMod>> classes = Sets.newHashSet();
	
	public boolean searchPackage(String packDir) {
		try {
		      return classes.addAll(
		          filterClassPaths(
		              getFMLClassLoader(),
		              ClassLoaderHelper.getClassPathsInPackage(getFMLClassLoader(), packDir)));
		    } catch (IOException e) {
		    	OnLogin.addError(new TAEModException(this.getClass(),"Search Package Error"));
		    	return false;
		    }
	}

	public void init() {
		for(Class<?> clazz : classes) {
			try {
	    		MinecraftForge.EVENT_BUS.register(clazz.newInstance());
			} catch (Exception e) {
		    	OnLogin.addError(new TAEModException(clazz,"Mod Initialization Error"));
			}
		}
	}
	
	@Override
	public Class<BaseMod> getInheritedClass() {
		return BaseMod.class;
	}

	@Override
	public Class<? extends Annotation> getAnnotationClass() {
		return null;
	}
	
}
