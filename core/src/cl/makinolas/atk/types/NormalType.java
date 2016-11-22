package cl.makinolas.atk.types;

public class NormalType extends AbstractType implements IType {

	private static NormalType instance = null;

	private NormalType(){
		this.isNormal = true;
	}

	public static NormalType getInstance(){
		if (instance == null){
			instance = new NormalType();
		}
		return instance;
	}

	@Override
	public double attackFromType(IType type) {
		return type.normalPokemonAttacks(this);
	}

	@Override
	public double attackToType(IType type) {
		return type.monsterHasAttackedFromNormal();
	}

	@Override
	public double normalPokemonAttacks(IType type) {
		return 1.5;
	}

	@Override
	public double monsterHasAttackedFromFight() {
		return 2;
	}

	@Override
	public double monsterHasAttackedFromGhost() {
		return 0;
	}

}