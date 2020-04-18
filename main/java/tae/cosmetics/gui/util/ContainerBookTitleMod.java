package tae.cosmetics.gui.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import tae.cosmetics.Globals;

public class ContainerBookTitleMod extends Container implements Globals {
	
	private IInventory displayBook;
	
	public String currentTitle = "Default Title";
	
	public ContainerBookTitleMod(int xOffset, int yOffset) {
		displayBook = new InventoryBookMod();
		
		Item possibleBookItem = Item.getByNameOrId("minecraft:written_book");	
		ItemWrittenBook bookItem;
		if(possibleBookItem == null || !(possibleBookItem instanceof ItemWrittenBook)) {
			bookItem = new ItemWrittenBook();
		} else {
			bookItem = (ItemWrittenBook) possibleBookItem;
		}
				
		ItemStack bookStack = new ItemStack(bookItem);
		bookStack.setTagInfo("author", new NBTTagString(mc.getSession().getUsername()));
		bookStack.setTagInfo("title", new NBTTagString("Default Title"));

		ItemStack stack = bookStack;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) nbt = new NBTTagCompound();
		NBTTagList lore = new NBTTagList();
		lore.appendTag(new NBTTagString("Click me to sign the book!"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);

		nbt.setTag("display", display);
		bookStack.setTagCompound(nbt);
		
		displayBook.setInventorySlotContents(0, bookStack);
		
		addSlotToContainer(new Slot(displayBook, 0, 147 + xOffset, 15 + yOffset) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
	            
			public boolean canTakeStack(EntityPlayer playerIn) {
				return false;
			}
		});
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	public void updateTitle(String newTitle) {
		ItemStack bookItem = this.getSlot(0).getStack();
		
		currentTitle = (newTitle.isEmpty())?"Default Title":newTitle;
		
		bookItem.setTagInfo("title", new NBTTagString(currentTitle));

	}
	
	public class InventoryBookMod implements IInventory {

	    private final NonNullList<ItemStack> book = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
		
		@Override
		public String getName() {
			return "booktitlemod";
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TextComponentString("Book Title");
		}

		@Override
		public int getSizeInventory() {
			return 1;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public ItemStack getStackInSlot(int index) {
			return this.book.get(0);
		}

		@Override
		public ItemStack decrStackSize(int index, int count) {
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack removeStackFromSlot(int index) {
			return ItemStack.EMPTY;
		}

		@Override
		public void setInventorySlotContents(int index, ItemStack stack) {
			this.book.set(0, stack);
		}

		@Override
		public int getInventoryStackLimit() {
			return 1;
		}

		@Override
		public void markDirty() {
		}

		@Override
		public boolean isUsableByPlayer(EntityPlayer player) {
			return true;
		}

		@Override
		public void openInventory(EntityPlayer player) {
			
		}

		@Override
		public void closeInventory(EntityPlayer player) {
			
		}

		@Override
		public boolean isItemValidForSlot(int index, ItemStack stack) {
			return false;
		}

		@Override
		public int getField(int id) {
			return 0;
		}

		@Override
		public void setField(int id, int value) {
		}

		@Override
		public int getFieldCount() {
			return 0;
		}

		@Override
		public void clear() {
		}
		
	}

}
